package com.wuhk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: SpringCloudAlibabaGatewayApplicatiom
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/30 20:05
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication
@EnableFeignClients
public class SpringCloudAlibabaGatewayApplicatiom {
    public static void main(String[] args) {
        System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(SpringCloudAlibabaGatewayApplicatiom.class,args);
    }
}
