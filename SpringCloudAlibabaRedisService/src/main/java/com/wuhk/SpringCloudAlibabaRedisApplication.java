package com.wuhk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @className: SpringCloudAlibabaRedisApplication
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/9 10:16
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.wuhk.mapper")
@ComponentScan(basePackages = "com.wuhk.**")
public class SpringCloudAlibabaRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaRedisApplication.class,args);
    }
}
