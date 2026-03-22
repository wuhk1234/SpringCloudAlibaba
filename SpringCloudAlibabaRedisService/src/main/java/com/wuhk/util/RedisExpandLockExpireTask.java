package com.wuhk.util;

import com.wuhk.service.impl.RedisServiceImpl;

/**
 * @className: RedisExpandLockExpireTask
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 19:37
 * @version: 1.0
 * @company 航天信息
 **/

public class RedisExpandLockExpireTask  implements Runnable{

    private String key;
    private String value;
    private long expire;
    private boolean isRunning;
    private RedisServiceImpl redisService;

    public RedisExpandLockExpireTask(String key, String value, long expire, RedisServiceImpl redisService) {
        this.key = key;
        this.value = value;
        this.expire = expire;
        this.redisService = redisService;
        this.isRunning = true;
    }

    @Override
    public void run() {
        //任务执行周期
        long waitTime = expire * 1000 * 2 / 3;
        while (isRunning){
            try {
                Thread.sleep(waitTime);
                if(redisService.luaScriptExpandLockExpire(key,value,expire)){
                    System.out.println("Lock expand expire success! " + value);
                }else{
                    stopTask();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopTask(){
        isRunning = false;
    }
}
