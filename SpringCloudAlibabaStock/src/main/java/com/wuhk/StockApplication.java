package com.wuhk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @className: StockApplication
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/9 16:18
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication
@EnableFeignClients
@MapperScan("com.wuhk.mapper")
@ComponentScan(basePackages = "com.wuhk.**")
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class,args);
    }
}
