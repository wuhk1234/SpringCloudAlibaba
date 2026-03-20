package com.wuhk.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: SpringCloudAlibabaAuthServerApplication
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/19 15:08
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class SpringCloudAlibabaAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaAuthServerApplication.class,args);
    }

}
