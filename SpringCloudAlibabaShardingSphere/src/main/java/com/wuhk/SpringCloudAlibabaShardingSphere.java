package com.wuhk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
/**
 * @className: SpringCloudAlibabaShardingSphere
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 13:49
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.wuhk.mapper")
@ComponentScan(basePackages = "com.wuhk.**")
public class SpringCloudAlibabaShardingSphere {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaShardingSphere.class,args);
    }
}
