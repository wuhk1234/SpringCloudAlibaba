package com.config;

import com.wuhk.interceptor.CoustomInterceptor;
import feign.Contract;
import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: FeginConfig
 * @description: 全局配置
 * @author: wuhk
 * @date: 2023/3/10 20:42
 * @version: 1.0
 * @company 航天信息
 **/
//@Configuration
public class FeginConfig {
    //日志级别(代码实现)
    //@Bean
    public Logger.Level feginLoggingLevel(){
        return Logger.Level.FULL;
    }

    //feign的契约加载(代码实现)：加上 @RequestLine("GET //stock/puduct") //契约配置时需要变为feign的接口传参方式，否则会报：Method SentinelSockService#puduct2() not annotated with HTTP method type (ex. GET, POST)
    //@Bean
    public Contract contract(){
        return new Contract.Default();
    }

    //设置fegin的超时时间(代码实现)
    //@Bean
    public Request.Options options(){
        return new Request.Options(3000,3000);
    }

    //设置自定义fegin的拦截器(代码实现)
    //@Bean
    public CoustomInterceptor coustomInterceptor(){
        return new CoustomInterceptor();
    }

}
