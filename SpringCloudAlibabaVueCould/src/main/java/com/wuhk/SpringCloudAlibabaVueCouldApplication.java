package com.wuhk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @className: SpringCloudAlibabaVueCouldApplication
 * @description: 启动器
 * @author: wuhk
 * @date: 2026/3/12 0012 17:38
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.wuhk.mapper")
@ComponentScan(basePackages = "com.wuhk.**")
public class SpringCloudAlibabaVueCouldApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaVueCouldApplication.class,args);
    }

}
