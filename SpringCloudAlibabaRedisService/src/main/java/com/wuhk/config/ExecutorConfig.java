package com.wuhk.config;

import java.util.concurrent.*;
/**
 * @className: ExecutorConfig
 * @description: TODO
 * @author: wuhk
 * @date: 2025/12/14 0014 16:00
 * @version: 1.0
 * @company 航天信息
 **/


public class ExecutorConfig {
    private static int maxPoolSize = Runtime.getRuntime().availableProcessors();
    private volatile static ExecutorService executorService;
    public static ExecutorService getThreadPool() {
        if (executorService == null){
            synchronized (ExecutorConfig.class){
                if (executorService == null){
                    executorService =  newThreadPool();
                }
            }
        }
        return executorService;
    }

    private static  ExecutorService newThreadPool(){
        int queueSize = 500;
        int corePool = Math.min(5, maxPoolSize);
        return new ThreadPoolExecutor(corePool, maxPoolSize, 10000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize),new ThreadPoolExecutor.AbortPolicy());
    }
    private ExecutorConfig(){}
}
