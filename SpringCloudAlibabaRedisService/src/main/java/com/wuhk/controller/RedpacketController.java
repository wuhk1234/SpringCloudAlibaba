package com.wuhk.controller;

import com.wuhk.common.ResponseData;
import com.wuhk.config.LuaConfiguration;
import com.wuhk.entity.RedPacketMessage;
import com.wuhk.entity.RedPacketQueue;
import com.wuhk.entity.Result;
import com.wuhk.service.RedpacketService;
import com.wuhk.util.DoubleUtil;
import com.wuhk.util.RedisUtil;
import com.wuhk.util.ResponseUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @className: RedpacketController
 * @description: 通过Redis实现抢红包功能
 * @author: wuhk
 * @date: 2026/3/21 0021 18:38
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/api/redpacket")
@Slf4j
public class RedpacketController {
    @Resource
    private LuaConfiguration luaConfiguration;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedpacketService redpacketService;

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * 创建线程池  调整队列数 拒绝服务
     */
    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000));

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 用户发红包
     * @param uid 用户ID
     * @param totalNum 红包总数
     * @param totalAmount 红包总额
     */
    @RequestMapping("/addRedPacket/{uid}/{totalNum}/{totalAmount}")
    public ResponseData addRedPacket(@PathVariable("uid") String uid, @PathVariable("totalNum") Integer totalNum,
                                     @PathVariable("totalAmount") Integer totalAmount){
        return ResponseUtils.response(redpacketService.addRedPacket(uid,totalNum,totalAmount));
    }

    /**
     * 用户抢红包
     * @param redPacketId 红包ID
     */
    @RequestMapping("/getRedPacket/{redPacketId}")
    public ResponseData getRedPacket(@PathVariable("redPacketId") String redPacketId){
        return ResponseUtils.response(redpacketService.getRedPacket(redPacketId));
    }

    /**
     * 用户拆红包
     * @param redPacketId 红包ID
     */
    @RequestMapping("/unpackRedPacket/{redPacketId}/{uid}")
    public ResponseData unpackRedPacket(@PathVariable("redPacketId") String redPacketId,@PathVariable("uid") String uid){
        return ResponseUtils.response(redpacketService.unpackRedPacket(redPacketId,uid));
    }

    @GetMapping("/lua")
    public ResponseEntity lua() {
        List<String> keys = Arrays.asList("testLua", "hello lua");
        Boolean execute = stringRedisTemplate.execute(luaConfiguration.redisScriptTest(), keys, "100");
        assert execute != null;
        return ResponseEntity.ok(execute);
    }


    /**
     * 抢红包 拆红包 抢到基本能拆到
     * 建议使用抢红包二的方式
     * @param redPacketId
     * @return
     */
    @ApiOperation(value="抢红包一",nickname="爪哇笔记")
    @PostMapping("/start")
    public Result start(long redPacketId){
        int skillNum = 100;
        final CountDownLatch latch = new CountDownLatch(skillNum);

         //初始化红包数据，抢红包拦截
        redisUtil.cacheValue(redPacketId+"-num",10);
        //初始化剩余人数，拆红包拦截
        redisUtil.cacheValue(redPacketId+"-restPeople",10);
        //初始化红包金额，单位为分
        redisUtil.cacheValue(redPacketId+"-money",20000);
        //模拟100个用户抢10个红包
        for(int i=1;i<=skillNum;i++){
            int userId = i;
            Runnable task = () -> {
                //抢红包拦截，其实应该分两步，为了演示方便
                long count = redisUtil.decr(redPacketId+"-num",1);
                if(count>=0){
                    Result result = redpacketService.startSeckil(redPacketId,userId);
                    Double amount = DoubleUtil.divide(Double.parseDouble(result.get("msg").toString()), (double) 100);
                    log.info("用户{}抢红包成功，金额：{}", userId,amount);
                }else{
                    log.info("用户{}抢红包失败",userId);
                }
                latch.countDown();
            };
            executor.execute(task);
        }
        try {
            latch.await();
            Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
            log.info("剩余金额：{}",restMoney);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    /**
     * 抢红包 拆红包 抢到不一定能拆到
     * @param redPacketId
     * @return
     */
    @ApiOperation(value="抢红包二",nickname="爪哇笔记")
    @PostMapping("/startTwo")
    public Result startTwo(long redPacketId){
        int skillNum = 100;
        final CountDownLatch latch = new CountDownLatch(skillNum);
        /**
         * 初始化红包数据，抢红包拦截
         */
        redisUtil.cacheValue(redPacketId+"-num",10);
        /**
         * 初始化红包金额，单位为分
         */
        redisUtil.cacheValue(redPacketId+"-money",20000);
        /**
         * 模拟100个用户抢10个红包
         */
        for(int i=1;i<=skillNum;i++){
            int userId = i;
            Runnable task = () -> {
                /**
                 * 抢红包 判断剩余金额
                 */
                Integer money = (Integer) redisUtil.getValue(redPacketId+"-money");
                if(money>0){
                    /**
                     * 虽然能抢到 但是不一定能拆到
                     * 类似于微信的 点击红包显示抢的按钮
                     */
                    Result result = redpacketService.startTwoSeckil(redPacketId,userId);
                    if(result.get("code").toString().equals("500")){
                        log.info("用户{}手慢了，红包派完了",userId);
                    }else{
                        Double amount = DoubleUtil.divide(Double.parseDouble(result.get("msg").toString()), (double) 100);
                        log.info("用户{}抢红包成功，金额：{}", userId,amount);
                    }
                }else{
                    /**
                     * 直接显示手慢了，红包派完了
                     */
                    //log.info("用户{}手慢了，红包派完了",userId);
                }
                latch.countDown();
            };
            executor.execute(task);
        }
        try {
            latch.await();
            Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
            log.info("剩余金额：{}",restMoney);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
    /**
     * 有人没抢 红包发多了
     * 红包进入延迟队列
     * 实现过期失效
     * @param redPacketId
     * @return
     */
    @ApiOperation(value="抢红包三",nickname="爪哇笔记")
    @PostMapping("/startThree")
    public Result startThree(long redPacketId){
        int skillNum = 9;
        final CountDownLatch latch = new CountDownLatch(skillNum);
        /**
         * 初始化红包数据，抢红包拦截
         */
        redisUtil.cacheValue(redPacketId+"-num",10);
        /**
         * 初始化红包金额，单位为分
         */
        redisUtil.cacheValue(redPacketId+"-money",20000);
        /**
         * 加入延迟队列 24s秒过期
         */
        RedPacketMessage message = new RedPacketMessage(redPacketId,24);
        RedPacketQueue.getQueue().produce(message);
        /**
         * 模拟 9个用户抢10个红包
         */
        for(int i=1;i<=skillNum;i++){
            int userId = i;
            Runnable task = () -> {
                /**
                 * 抢红包 判断剩余金额
                 */
                Integer money = (Integer) redisUtil.getValue(redPacketId+"-money");
                if(money>0){
                    Result result = redpacketService.startTwoSeckil(redPacketId,userId);
                    if(result.get("code").toString().equals("500")){
                        log.info("用户{}手慢了，红包派完了",userId);
                    }else{
                        Double amount = DoubleUtil.divide(Double.parseDouble(result.get("msg").toString()), (double) 100);
                        log.info("用户{}抢红包成功，金额：{}", userId,amount);
                    }
                }
                latch.countDown();
            };
            executor.execute(task);
        }
        try {
            latch.await();
            Integer restMoney = Integer.parseInt(redisUtil.getValue(redPacketId+"-money").toString());
            log.info("剩余金额：{}",restMoney);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
}
