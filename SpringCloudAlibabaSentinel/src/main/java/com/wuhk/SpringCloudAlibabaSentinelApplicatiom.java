package com.wuhk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: SpringCloudAlibabaSentinelApplicatiom
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/14 15:00
 * @version: 1.0
 * @company 航天信息
 **/
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableFeignClients
public class SpringCloudAlibabaSentinelApplicatiom {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAlibabaSentinelApplicatiom.class,args);
    }
}
