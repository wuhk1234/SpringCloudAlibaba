package com.wuhk.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.wuhk.entity.Employment;
import com.wuhk.entity.OrderInfo;
import com.wuhk.entity.OrderInfoExcel;
import com.wuhk.listener.UploadDataListener;
import com.wuhk.mapper.EmploymentMapper;
import com.wuhk.service.EmploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @className: EmploymentServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/16 0016 21:15
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl extends ServiceImpl<EmploymentMapper, Employment> implements EmploymentService {
    @Autowired
    private EmploymentMapper employmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcle(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(),Employment.class,new UploadDataListener<Employment>(
                list -> {
                    // 校验数据
//                                        ValidationUtils.validate(list);
                    // dao 保存···
                    //最好是手写一个，不要使用mybatis-plus的一条条新增的逻辑
                    this.saveBatch(list);
                    log.info("从Excel导入数据一共 {} 行 ", list.size());
                }))
                .sheet()
                .doRead();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcleAsync(MultipartFile file)throws IOException {
        ExecutorService  executorService = Executors.newFixedThreadPool(5);
        List<Callable<Object>> listData = new ArrayList<>();
        //int sheetNo = 0; // Excel中的sheet索引，从0开始
        for (int i = 0; i < 5; i++) {
            int sheetNo = i;
            listData.add(()->{
                try {
                    EasyExcel.read(file.getInputStream(),Employment.class,new UploadDataListener<Employment>(
                            list -> {
                                // 校验数据
                                //ValidationUtils.validate(list);
                                // dao 保存···
                                //最好是手写一个，不要使用mybatis-plus的一条条新增的逻辑
                                this.saveBatch(list);
                                log.info("从Excel导入数据一共 {} 行 ", list.size());
                            }))
                            .sheet(sheetNo)
                            .doRead();

                }catch (Exception e){
                    log.error("处理sheet {} 时发生错误",sheetNo,e);
                }
                return null;
            });
        }
        try {
            executorService.invokeAll(listData);
        }catch (Exception e){
            log.error("处理sheet {} 时发生错误",e);
            Thread.currentThread().interrupt();
        }finally {
            executorService.shutdown();
        }
        log.info("多线程Excel数据导入完成");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importExcleAsync2(MultipartFile file)throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(5); // 创建5个线程的线程池
        Class<?> clazz = Employment.class; // 你的数据模型类
        int chunkSize = 5000; // 每个任务处理的行数，可以根据实际情况调整以优化性能
        long startTime = System.currentTimeMillis(); // 记录开始时间
        // 创建读取任务并提交到线程池中执行
        for (int i = 0; i < 10; i++) {// 根据sheeit数量循环，此处简化处理只处理一个sheet为例
            int sheetNo = i; // Excel中的sheet索引，从0开始
            executor.submit(() -> {
                try {
                    EasyExcel.read(file.getInputStream(), clazz, new UploadDataListener<Employment>(
                            list -> {
                                // 校验数据
//                                        ValidationUtils.validate(list);
                                // dao 保存···
                                //最好是手写一个，不要使用mybatis-plus的一条条新增的逻辑
                                this.saveBatch(list);
                                log.info("从Excel导入数据一共 {} 行 ", list.size());
                            })).sheet(sheetNo).doRead(); // 这里可以根据实际情况调整sheet的处理逻辑，例如分sheet处理等
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown(); // 关闭线程池，不再接受新的任务
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // 等待所有任务完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 处理中断异常
        }
        long endTime = System.currentTimeMillis(); // 记录结束时间
        System.out.println("Import completed in " + (endTime - startTime) + " ms"); // 输出耗时信息
        log.info("多线程Excel数据导入完成");
    }

    @Override
    public void expoerExcle1(HttpServletResponse response) throws IOException {
        exportSetHeader(response,"员工数据导出");
        try {
            List<Employment> list = employmentMapper.selectList(null);
            // 写出数据到浏览器端
            EasyExcel.write(response.getOutputStream(), Employment.class).sheet("员工数据导出").doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void expoerExcle3(HttpServletResponse response) throws IOException {
        exportSetHeader(response,"员工数据导出");
        long total = employmentMapper.selectCount(null);
        int pages = 5;
        long pageSize = total / pages +(total % pages > 0 ? 1 : 0);
        try(ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(),Employment.class).build()) {
            for (int i = 0; i < pages; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i,"第"+(i+1)+"页").build();
                Page<Employment> page = new Page<>();
                page.setCurrent(i+1);
                page.setSize(pageSize);
                Page<Employment> employmentPage = employmentMapper.selectPage(page,null);
                excelWriter.write(employmentPage.getRecords(),writeSheet);
            }
        }
    }

    @Override
    public void expoerExcle4(HttpServletResponse response) throws IOException, InterruptedException {
        exportSetHeader(response,"员工数据导出");
        //查询出总数据量大小，这里为500万
        long total = employmentMapper.selectCount(null);
        //根据总数得到总页数
        int pages= (int) ((total-1)/200+1);//总页数,每页200行数据
        System.out.println("pages="+pages);
        //xlsx每个sheet页最大数据行为1048576,超过这个数值就会报错,所以若有500万数据，每个sheet页100万则
        // 分5个导出到5个sheet页
        //根据总数得到每页sheet应该分几个sheet,每个sheet导入100万数据,500万就是5个sheet
        //long totalSheet=(total-1)/200+1;//总sheet页数,每个sheet200行数据
        //System.out.println("totalSheet="+totalSheet);

        int pageSize = 200;
        ExecutorService  executorService = Executors.newFixedThreadPool(pages);
        CountDownLatch countDownLatch = new CountDownLatch(pages);
        ConcurrentHashMap<Integer,List<Employment>> map = new ConcurrentHashMap<>();
        for (int i = 0; i < pages; i++) {
            int finali = i;
            executorService.submit(()->{
                try {
                    /*Page<Employment> page = new Page<>();
                    page.setCurrent(finali+1);
                    page.setSize(pageSize);*/
                    List<Employment> dataList = employmentMapper.batchExportNotParam();

                    //Page<Employment> employmentPage = employmentMapper.selectPage(page,null);
                    map.put(finali,dataList);
                }catch (Exception e){

                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        try(ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(),Employment.class).build()) {
            for (int i = 0; i < pages; i++) {
                List<Employment> list = map.get(i);
                if (list != null &&!list.isEmpty()) {
                    //List<List<Employment>> partition = ListUtils.partition(list, 200);
                    WriteSheet writeSheet = EasyExcel.writerSheet(i, "第" + (i + 1) + "页").build();
                    excelWriter.write(list,writeSheet);
                }
            }
        }
    }

    @Override
    public void expoerExcle5(HttpServletResponse response) throws IOException, InterruptedException {
        try(OutputStream outputStream =response.getOutputStream()){
            //查询出总数据量大小，这里为500万
            long count = employmentMapper.selectCount(null);
            System.out.println("count="+count);
            //根据总数得到总页数
            int totalPage= (int) ((count-1)/200+1);//总页数,每页10万行数据
            System.out.println("totalPage="+totalPage);
            //xlsx每个sheet页最大数据行为1048576,超过这个数值就会报错,所以这里将500万数据分5个导出到5个sheet页
            //根据总数得到每页sheet应该分几个sheet,每个sheet导入100万数据,500万就是5个sheet
            int totalSheet= (int) ((count-1)/200+1);//总sheet页数,每个sheet100万行数据
            System.out.println("totalSheet="+totalSheet);
            //设置初始页数
            OrderInfo orderInfo=new OrderInfo();
            orderInfo.setPageNum(0);//初始页,从0开始
            orderInfo.setPageSize(200);//每页返回数据
            //文件名
            String fileName="批量测试导出.xlsx";
            //使用EasyExcel进行导出
            ExcelWriter excelWriter = EasyExcel.write(outputStream, OrderInfoExcel.class).build();
            //这里最终会写到5个sheet里面
            for (int i = 0; i < totalSheet; i++) {
                //writerSheet第一个参数表示往几个sheet开始写数据,从0开始表示第一个
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "模板" + i).build();
                //分页去数据库查询数据
                for(int j = orderInfo.getPageNum(); j < totalPage; j++){
                    //页数,对应后端数据库来说是索引
                    int pageNum=j*200;
                    //每页要查询的行数
                    int pageSize=orderInfo.getPageSize();
                    //根据分页参数去查询每页数据
                    /*Page<Employment> page = new Page<>();
                    page.setCurrent(pageNum);
                    page.setSize(pageSize);*/
                    //Page<Employment> employmentPage = employmentMapper.selectPage(page,null);
                    List<Employment> dataList = employmentMapper.batchExport(pageNum,pageSize);
                    excelWriter.write(dataList, writeSheet);
                    System.out.println("已导出数据:"+(pageNum+200));
                    if((pageNum+200)%200==0){
                        //记录当前页数j并加1,并跳出这个for循环,往下一个sheet页写入数据
                        orderInfo.setPageNum(j+1);
                        break;
                    }
                }
            }
            //下载
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");//以流的形式对文件进行下载
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));//对文件名编码,防止文件名乱码
            excelWriter.finish();
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void importGenericListenerExcle(MultipartFile file) {
        //GenericEasyExcleListener<Employment> listener = new GenericEasyExcleListener<Employment>();
    }

    @Override
    @Async
    public CompletableFuture<Integer> importUsers(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader excelReader = EasyExcel.read(inputStream, Employment.class, new UploadDataListener<Employment>(
                list -> {
                    // 校验数据
                    //ValidationUtils.validate(list);
                    // dao 保存···
                    //最好是手写一个，不要使用mybatis-plus的一条条新增的逻辑
                    this.saveBatch(list);
                    log.info("从Excel导入数据一共 {} 行 ", list.size());
                })).build();
        excelReader.readAll();
        excelReader.finish();
        return CompletableFuture.completedFuture(1);
    }

    private void exportSetHeader(HttpServletResponse response,String fileName) throws IOException {
        // 设置响应结果类型
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileNameURLEncoder = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileNameURLEncoder + ".xlsx");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
    }

}
