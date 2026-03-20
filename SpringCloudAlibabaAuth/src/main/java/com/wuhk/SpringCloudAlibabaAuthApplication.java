package com.wuhk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @className: SpringCloudAlibabaAuthApplication
 * @description: 认证服务
 * @author: wuhk
 * @date: 2023/7/8 9:31
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication
@EnableFeignClients
@EnableAuthorizationServer
@MapperScan("com.wuhk.**")
@ComponentScan(basePackages = "com.wuhk.**")
public class SpringCloudAlibabaAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaAuthApplication.class,args);
    }
}
