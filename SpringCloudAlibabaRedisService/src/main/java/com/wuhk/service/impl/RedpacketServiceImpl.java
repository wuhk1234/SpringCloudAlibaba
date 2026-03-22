package com.wuhk.service.impl;

import com.wuhk.common.ServiceResponse;
import com.wuhk.entity.RedPacketInfo;
import com.wuhk.entity.RedPacketInfoExample;
import com.wuhk.entity.RedPacketRecord;
import com.wuhk.entity.Result;
import com.wuhk.mapper.RedPacketInfoMapper;
import com.wuhk.mapper.RedPacketRecordMapper;
import com.wuhk.service.RedpacketService;
import com.wuhk.util.RedisExpandLockExpireTask;
import com.wuhk.util.RedisUtil;
import com.wuhk.util.RedissLockUtil;
import com.wuhk.util.ResponseCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @className: RedpacketServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 18:48
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class RedpacketServiceImpl implements RedpacketService {
    //@Autowired
    //private UserBloomFilterServiceImpl userBloomFilterService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private RedPacketInfoMapper redPacketInfoMapper;

    @Autowired
    private RedPacketRecordMapper redPacketRecordMapper;

    public static final String REMAINING_NUM = ":remaining_num";
    public static final String REMAINING_AMOUNT = ":remaining_amount";
    public static final String UNPACK_LOCK_PRE = "unpack_lock_prefix_";

    @Value("${server.port}")
    private String port;

    @Override
    public ServiceResponse addRedPacket(String uid, Integer totalNum, Integer totalAmount) {
        ServiceResponse serviceResponse = new ServiceResponse();
        Date currentDate = new Date(System.currentTimeMillis());
        //校验当前用户是否是真实用户
        /*Boolean userExist = userBloomFilterService.bloomFilterExist(uid);
        if(!userExist){
            serviceResponse.setSuccess(false);
            serviceResponse.setCode(ResponseCodeEnum.USER_NOTEXIST_EXCEPTION.getCode());
            serviceResponse.setMsg(ResponseCodeEnum.USER_NOTEXIST_EXCEPTION.getDescribe());
            return serviceResponse;
        }*/
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        //这里使用简单的UUID方式生成ID,分布式唯一键建议大家可以了解其他方式生成，例如雪花算法等
        redPacketInfo.setRedPacketId(StringUtils.replace(java.util.UUID.randomUUID().toString(), "-", "").toUpperCase());
        redPacketInfo.setTotalAmount(totalAmount);
        redPacketInfo.setTotalPacket(totalNum);
        redPacketInfo.setRemainingAmount(totalAmount);
        redPacketInfo.setRemainingPacket(totalNum);
        redPacketInfo.setUid(uid);
        redPacketInfo.setCreateTime(currentDate);
        redPacketInfo.setUpdateTime(currentDate);
        boolean result = saveOrUpdateRedPacketInfo(redPacketInfo,null);
        serviceResponse.setSuccess(result);
        if(result){
            //将红包信息(剩余包数量、剩余包总额)添加到Redis中
            redisService.set(redPacketInfo.getRedPacketId() + REMAINING_NUM,redPacketInfo.getRemainingPacket());
            redisService.set(redPacketInfo.getRedPacketId() + REMAINING_AMOUNT,redPacketInfo.getRemainingAmount());
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse getRedPacket(String redPacketId) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setSuccess(true);
        Object remainingNumObj = redisService.get(redPacketId + REMAINING_NUM);
        if(null == remainingNumObj){
            serviceResponse.setSuccess(false);
            serviceResponse.setCode(ResponseCodeEnum.RED_PACKET_NOTEXIST_EXCEPTION.getCode());
            serviceResponse.setMsg(ResponseCodeEnum.RED_PACKET_NOTEXIST_EXCEPTION.getDescribe());
            return serviceResponse;
        }
        int remainingNum = Integer.parseInt(String.valueOf(remainingNumObj));
        if(remainingNum <= 0){
            serviceResponse.setSuccess(false);
            serviceResponse.setCode(ResponseCodeEnum.RED_PACKET_FINISH_EXCEPTION.getCode());
            serviceResponse.setMsg(ResponseCodeEnum.RED_PACKET_FINISH_EXCEPTION.getDescribe());
            return serviceResponse;
        }
        serviceResponse.setData(remainingNum);
        return serviceResponse;
    }

    @Override
    public ServiceResponse unpackRedPacket(String redPacketId, String uid) {
        ServiceResponse serviceResponse = new ServiceResponse();
        String lockName = UNPACK_LOCK_PRE + redPacketId;
        String currentValue = RedisServiceImpl.getHostIp() + ":" + port;
        Boolean luaResult = false;
        Long expire = 60L;
        try {
            //设置锁
            luaResult = redisService.luaScript(lockName,currentValue,expire);

            if(luaResult){
                //开启守护线程 定期检测 续锁 执行拆红包业务
                serviceResponse = executeBusiness(lockName, currentValue, expire, redPacketId, uid);
            }else{
                //获取锁失败
                String value = (String) redisService.get(lockName);
                //校验锁内容 支持可重入性
                if(currentValue.equals(value)){
                    Boolean expireResult = redisService.expire(lockName, expire);
                    if(expireResult){
                        serviceResponse = executeBusiness(lockName,currentValue,expire,redPacketId, uid);
                    }
                }
                System.out.println("Lock fail,current lock belong to:" + value);
            }
        }catch (Exception e){
            System.out.println("unpackRedPacket exception:" + e);
        }finally {
            if(luaResult){
                //若分布式锁Value与本机Value一致，则当前机器获得锁，进行解锁
                Boolean releaseLock = redisService.luaScriptReleaseLock(lockName, currentValue);
                if(releaseLock){
                    System.out.println("release lock success");
                }else{
                    System.out.println("release lock fail");
                }
            }else{
                //获取锁失败
                serviceResponse.setSuccess(false);
                serviceResponse.setCode(ResponseCodeEnum.SERVICE_BUSINESS_ERROR.getCode());
                serviceResponse.setMsg(ResponseCodeEnum.SERVICE_BUSINESS_ERROR.getDescribe());
            }
        }
        return serviceResponse;
    }


    /**
     * 执行拆红包业务
     */
    private ServiceResponse executeBusiness(String lockName,String currentValue,Long expire,String redPacketId, String uid) throws InterruptedException {
        System.out.println("Lock success,execute business,current time:" + System.currentTimeMillis());
        //开启守护线程 定期检测 续锁
        RedisExpandLockExpireTask expandLockExpireTask = new RedisExpandLockExpireTask(lockName,currentValue,expire,redisService);
        Thread thread = new Thread(expandLockExpireTask);
        thread.setDaemon(true);
        thread.start();
        //执行拆红包业务
        //检验红包是否存在以及是否还有剩余
        ServiceResponse serviceResponse = getRedPacket(redPacketId);
        if(!serviceResponse.isSuccess()){
            return serviceResponse;
        }
        serviceResponse.setSuccess(true);
        Integer remainingNum = (Integer) redisService.get(redPacketId + REMAINING_NUM);
        Integer remainingAmount = (Integer) redisService.get(redPacketId + REMAINING_AMOUNT);
        if(remainingNum <= 0 || remainingAmount <= 0){
            //Redis中剩余金额或者数量为空
            serviceResponse.setSuccess(false);
            serviceResponse.setCode(ResponseCodeEnum.SERVICE_BUSINESS_ERROR.getCode());
            serviceResponse.setMsg(ResponseCodeEnum.SERVICE_BUSINESS_ERROR.getDescribe());
            return serviceResponse;
        }
        //检验当前用户是否已经抢过该红包
        RedPacketRecord redPacketRecordEmp = new RedPacketRecord();
        redPacketRecordEmp.setUid(uid);
        redPacketRecordEmp.setRedPacketId(redPacketId);
        List<RedPacketRecord> redPacketRecords = redPacketRecordMapper.selectByExample(redPacketRecordEmp);
        if(!CollectionUtils.isEmpty(redPacketRecords)){
            //该用户已抢过当前红包
            serviceResponse.setSuccess(false);
            serviceResponse.setCode(ResponseCodeEnum.RED_PACKET_REPEAT_UNPACK_EXCEPTION.getCode());
            serviceResponse.setMsg(ResponseCodeEnum.RED_PACKET_REPEAT_UNPACK_EXCEPTION.getDescribe());
            return serviceResponse;
        }
        //Integer remainingNum = Integer.parseInt(remainingNumStr);
        //Integer remainingAmount = Integer.parseInt(remainingAmountStr);
        //红包金额算法，此处大家可以自己去更深入查阅一些关于红包算法各种实现的资料
        Integer randomAmount;
        if(remainingNum == 1){
            //若为最后一个红包则拆得全部金额 并删除红包在缓存中数据
            randomAmount = remainingAmount;
            redisService.remove(redPacketId + REMAINING_NUM);
            redisService.remove(redPacketId + REMAINING_AMOUNT);
        }else{
            Integer maxRandomAmount = remainingAmount / remainingNum * 2;
            randomAmount = new Random().nextInt(maxRandomAmount);
            redisService.decr(redPacketId + REMAINING_NUM,1);
            redisService.decr(redPacketId + REMAINING_AMOUNT,randomAmount);
        }
        //添加拆红包记录 以及红包信息更新 (此处更完善需考虑操作DB是否成功 失败后如何处理)
        Date currentDate = new Date(System.currentTimeMillis());
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setUid(uid);
        redPacketRecord.setRedPacketId(redPacketId);
        redPacketRecord.setCreateTime(currentDate);
        redPacketRecord.setAmount(randomAmount);
        saveOrUpdateRedPacketRecord(redPacketRecord);
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        RedPacketInfoExample redPacketInfoExample = new RedPacketInfoExample();
        redPacketInfo.setUpdateTime(currentDate);
        redPacketInfo.setRemainingPacket(remainingNum - 1);
        redPacketInfo.setRemainingAmount(remainingAmount - randomAmount);
        redPacketInfoExample.setRedPacketId(redPacketId);
        saveOrUpdateRedPacketInfo(redPacketInfo,redPacketInfoExample);
        serviceResponse.setData(randomAmount);
        return serviceResponse;
    }
    @Override
    public RedPacketInfo get(long redPacketId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result startSeckil(long redPacketId,int userId) {
        Integer money = 0;
        boolean res=false;
        try {
            /**
             * 获取锁
             */
            res = RedissLockUtil.tryLock(redPacketId+"", TimeUnit.SECONDS, 3, 10);
            if(res){
                long restPeople = redisUtil.decr(redPacketId+"-restPeople",1);
                /**
                 * 如果是最后一人
                 */
                if(restPeople==0){
                    money = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
                }else{
                    Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
                    Random random = new Random();
                    //随机范围：[1,剩余人均金额的两倍]
                    money = random.nextInt((int) (restMoney / (restPeople+1) * 2 - 1)) + 1;
                }
                redisUtil.decr(redPacketId+"-money",money);
                /**
                 * 异步入库
                 */
                RedPacketRecord record = new RedPacketRecord();
                record.setAmount(money);
                record.setRedPacketId(String.valueOf(redPacketId));
                record.setUid(String.valueOf(userId));
                record.setNickName("wuhhk");
                record.setImgUrl("https://github.com/wuhk1234");
                record.setCreateTime(new Timestamp(System.currentTimeMillis()));
                record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                saveRecord(record);
                /**
                 * 异步入账
                 */
            }else{
                /**
                 * 获取锁失败相当于抢红包失败，红包个数加一
                 */
                redisUtil.incr(redPacketId+"-num",1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放锁
            if(res){
                RedissLockUtil.unlock(redPacketId+"");
            }
        }
        return Result.ok(money);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result startTwoSeckil(long redPacketId, int userId) {
        Integer money = 0;
        boolean res=false;
        try {
            /**
             * 获取锁 保证红包数量和计算红包金额的原子性操作
             */
            res = RedissLockUtil.tryLock(redPacketId+"", TimeUnit.SECONDS, 3, 10);
            if(res){
                long restPeople = redisUtil.decr(redPacketId+"-num",1);
                if(restPeople<0){
                    return Result.error("手慢了，红包派完了");
                }else{
                    /**
                     * 如果是最后一人
                     */
                    if(restPeople==0){
                        money = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
                    }else{
                        Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
                        Random random = new Random();
                        //随机范围：[1,剩余人均金额的两倍]
                        money = random.nextInt((int) (restMoney / (restPeople+1) * 2 - 1)) + 1;
                    }
                    redisUtil.decr(redPacketId+"-money",money);
                    /**
                     * 异步入库
                     */
                    RedPacketRecord record = new RedPacketRecord();
                    record.setAmount(money);
                    record.setRedPacketId(String.valueOf(redPacketId));
                    record.setUid(String.valueOf(userId));
                    record.setNickName("wuhhk");
                    record.setImgUrl("https://github.com/wuhk1234");
                    record.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    saveRecord(record);
                    /**
                     * 异步入账
                     */
                }
            }else{
                /**
                 * 获取锁失败相当于抢红包失败
                 */
                return Result.error("手慢了，红包派完了");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //释放锁
            if(res){
                RedissLockUtil.unlock(redPacketId+"");
            }
        }
        return Result.ok(money);
    }

    @Async
    void saveRecord(RedPacketRecord record){
        redPacketRecordMapper.insertSelective(record);
    }
    private boolean saveOrUpdateRedPacketInfo(RedPacketInfo redPacketInfo, RedPacketInfoExample redPacketInfoExample){
        boolean result = false;
        int flag;
        if(null == redPacketInfo){
            return result;
        }
        if(null != redPacketInfo.getId() || null != redPacketInfoExample){
            if(null != redPacketInfoExample){
                flag = redPacketInfoMapper.updateByExampleSelective(redPacketInfo,redPacketInfoExample);
            }else{
                flag = redPacketInfoMapper.updateByPrimaryKeySelective(redPacketInfo);
            }
        }else{
            flag = redPacketInfoMapper.insertSelective(redPacketInfo);
        }
        if(flag > 0){
            result = true;
        }
        return result;
    }

    private boolean saveOrUpdateRedPacketRecord(RedPacketRecord redPacketRecord){
        boolean result = false;
        int flag;
        if(null == redPacketRecord){
            return result;
        }
        if(null != redPacketRecord.getId()){
            flag = redPacketRecordMapper.updateByPrimaryKeySelective(redPacketRecord);
        }else{
            flag = redPacketRecordMapper.insertSelective(redPacketRecord);
        }
        if(flag > 0){
            result = true;
        }
        return result;
    }
}
