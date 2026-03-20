package com.wuhk.service.impl;

import com.google.protobuf.ServiceException;
import com.wuhk.config.ExecutorConfig;
import com.wuhk.config.ListPublicUse;
import com.wuhk.config.SqlContext;
import com.wuhk.entity.SysUserAddress;
import com.wuhk.mapper.ThreadTransactionMapper;
import com.wuhk.service.ThreadTransactionalService;
import io.seata.common.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @className: ThreadTransactionalServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/2 0002 16:50
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@Service
public class ThreadTransactionalServiceImpl implements ThreadTransactionalService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ThreadTransactionMapper threadTransactionMapper;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Resource
    private SqlContext sqlContext;
    @Override
    @Transactional
    public void a() throws InterruptedException {
        ConnectionHolder connectionHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
        /*String sql = "INSERT INTO `seata-stock`.`t_user` (`id`, `username`, `password`, `user_role`) VALUES ('1', 'tiankong', '221014', 'dd')";
        jdbcTemplate.execute(sql);*/
        threadTransactionMapper.ThreadTransactionalInsertA();
        Thread thread = new Thread(()->{
            TransactionSynchronizationManager.bindResource(dataSource,connectionHolder);
            this.b();
        });
        thread.start();
        thread.join();
    }

    @Override
    @Transactional
    public void b() {
        /*String sql = "INSERT INTO `seata-stock`.`t_user` (`id`, `username`, `password`, `user_role`) VALUES ('1', 'tiankong', '221014', 'dd')";
        jdbcTemplate.execute(sql);*/
        threadTransactionMapper.ThreadTransactionalInsertB();
        throw new RuntimeException("异常了");
    }




    List<TransactionStatus> transactionStatuses = Collections.synchronizedList(new ArrayList<>());

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void testMultiThreadTransactional(){

        //模拟总数据
        List<SysUserAddress> sysUserAddresses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sysUserAddresses.add(new SysUserAddress(i+1, "上海市" + (i + 1), "上海市", "浦东新区"));
        }

        //线程数，按线程数拆分，默认3个线程
        int threadCount = 3;
        //按线程数平均分配后，每个线程处理的数据量
        int perThreadData = sysUserAddresses.size() / threadCount;
        //按线程数平均分配后，多余的数据量
        int remainderCount = sysUserAddresses.size() % threadCount;
        //有多余的数据，再开个线程处理
        boolean havingRemainder = remainderCount > 0;
        if (havingRemainder) {
            threadCount += 1;
        }

        //子线程倒计锁
        CountDownLatch threadLatchs = new CountDownLatch(threadCount);
        //子线程中是否有异常标识
        AtomicBoolean isError = new AtomicBoolean(false);
        ExecutorService executorService = Executors.newFixedThreadPool(5);//银行有五个服务员
        try {
            for (int i = 0; i < threadCount; i++) {
                //设置每个线程处理的数据量，多余的数据放在最后一个线程中处理
                List<SysUserAddress> splitList = sysUserAddresses.stream()
                        .skip((long) i * perThreadData)
                        .limit((i == threadCount - 1 && havingRemainder)
                                ? remainderCount
                                : perThreadData
                        )
                        .collect(Collectors.toList());

                //开启多线程
                executorService.execute(() -> {
                    try {
                        try {
                            this.saveSysUserAddressByTransaMan(dataSourceTransactionManager, transactionStatuses, splitList);
                        } catch (Throwable e) {
                            e.printStackTrace();
                            isError.set(true);
                        } finally {
                            threadLatchs.countDown();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        isError.set(true);
                    }
                });
            }

            // 倒计锁设置超时时间 30s
            boolean await = threadLatchs.await(300, TimeUnit.SECONDS);
            // 判断是否超时
            if (!await) {
                isError.set(true);
                log.error("等待子线程执行已经超时!");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            isError.set(true);
        }

        /*if (CollectionUtils.isNotEmpty(transactionStatuses)) {
            if (isError.get()) {
                transactionStatuses.forEach(status -> {
                    if (!status.isCompleted()) {
                        dataSourceTransactionManager.rollback(status);
                    }
                });
            } else {
                for (TransactionStatus transactionStatus : transactionStatuses) {
                    if (!transactionStatus.isCompleted()) {
                        dataSourceTransactionManager.commit(transactionStatus);
                    }
                }
                *//*transactionStatuses.forEach(status -> {
                    if (!status.isCompleted()) {
                        dataSourceTransactionManager.commit(status);
                    }
                });*//*
            }
        }*/

        System.out.println("主线程完成!");

    }








    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void saveSysUserAddressByTransaMan(PlatformTransactionManager transactionManager, List<TransactionStatus> transactionStatuses, List<SysUserAddress> sysUserAddressList) {
        if (CollectionUtils.isEmpty(sysUserAddressList)) {
            return;
        }

        //将事务状态都放在同一个事务里面
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(Propagation.REQUIRES_NEW.value());                   // 事物隔离级别，每个线程都开启新事务，会比较安全一些
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);   // 获得事务状态
        transactionStatuses.add(transactionStatus);

        sysUserAddressList.forEach(obj -> {
//            if (StrUtil.equals(obj.getProvince(), "上海市2")) {
//                //模拟子线程中保存出现异常
//                int i = 1 / 0;
//            }
            synchronized (obj) {
                threadTransactionMapper.ThreadTransactionalInsertC(obj);
            }
        });
        System.out.println("子线程：" + Thread.currentThread().getName());
    }




    /**
     * 测试多线程事务.
     * @param employeeDOList
     */
    @Override
    public void saveThread(List<SysUserAddress> employeeDOList) throws SQLException, ServiceException {
        // 获取数据库连接,获取会话(内部自有事务)
        SqlSession sqlSession = sqlContext.getSqlSession();
        Connection connection = sqlSession.getConnection();
        try {
            // 设置手动提交
            connection.setAutoCommit(false);
            //获取mapper
            ThreadTransactionMapper employeeMapper = sqlSession.getMapper(ThreadTransactionMapper.class);
            //先做删除操作
            employeeMapper.delete(null);
            //获取执行器
            ExecutorService service = ExecutorConfig.getThreadPool();
            List<Callable<Integer>> callableList  = new ArrayList<>();
            //拆分list
            List<List<SysUserAddress>> lists= ListPublicUse.averageAssign(employeeDOList, 5);
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            for (int i =0;i<lists.size();i++){
                if (i==lists.size()-1){
                    atomicBoolean.set(false);
                }
                List<SysUserAddress> list  = lists.get(i);
                //使用返回结果的callable去执行,
                Callable<Integer> callable = () -> {
                    //让最后一个线程抛出异常
                    if (!atomicBoolean.get()){
                        throw new ServiceException("001",
                                new Throwable());
                    }
                    return null;//employeeMapper.saveBatch(list);
                };
                callableList.add(callable);
            }
            //执行子线程
            List<Future<Integer>> futures = service.invokeAll(callableList);
            for (Future<Integer> future:futures) {
                //如果有一个执行不成功,则全部回滚
                if (future.get()<=0){
                    connection.rollback();
                    return;
                }
            }
            connection.commit();
            System.out.println("添加完毕");
        }catch (Exception e){
            connection.rollback();
            log.info("error",e);
            throw new ServiceException("002",new Throwable());
        }finally {
            connection.close();
        }
    }

    /**
     * 测试多线程事务.
     * @param employeeDOList
     */
    @Override
    @Transactional
    public void saveThread1(List<SysUserAddress> employeeDOList) throws ServiceException {
        try {
            //先做删除操作,如果子线程出现异常,此操作不会回滚
            threadTransactionMapper.delete(null);
            //获取线程池
            ExecutorService service = ExecutorConfig.getThreadPool();
            //拆分数据,拆分5份，公众号Java精选，有惊喜！
            List<List<SysUserAddress>> lists=ListPublicUse.averageAssign(employeeDOList, 5);
            //执行的线程
            Thread []threadArray = new Thread[lists.size()];
            //监控子线程执行完毕,再执行主线程,要不然会导致主线程关闭,子线程也会随着关闭
            CountDownLatch countDownLatch = new CountDownLatch(lists.size());
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            for (int i =0;i<lists.size();i++){
                if (i==lists.size()-1){
                    atomicBoolean.set(false);
                }
                List<SysUserAddress> list  = lists.get(i);
                threadArray[i] =  new Thread(() -> {
                    try {
                        //最后一个线程抛出异常
                        if (!atomicBoolean.get()){
                            throw new RuntimeException("001",null);
                        }
                        //批量添加,mybatisPlus中自带的batch方法
                        threadTransactionMapper.saveBatch(list);
                    } finally {
                        countDownLatch.countDown();
                    }

                });
            }
            for (int i = 0; i <lists.size(); i++){
                service.execute(threadArray[i]);
            }
            //当子线程执行完毕时,主线程再往下执行
            countDownLatch.await();
            System.out.println("添加完毕");
        }catch (Exception e){
            log.info("error",e);
            throw new RuntimeException("002",new Throwable());
        }
    }
}
