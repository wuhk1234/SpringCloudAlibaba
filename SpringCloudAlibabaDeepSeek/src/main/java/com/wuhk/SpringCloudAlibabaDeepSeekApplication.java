package com.wuhk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @className: SpringCloudAlibabaDeepSeekApplication
 * @description: TODO
 * @author: wuhk
 * @date: 2025/10/3 0003 16:14
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = "com.wuhk.**")
public class SpringCloudAlibabaDeepSeekApplication {
    public static void main(String[] args) {

        SpringApplication.run(SpringCloudAlibabaDeepSeekApplication.class,args);
    }
}
