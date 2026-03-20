package com.wuhk.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @className: DataSourceConfig
 * @description: 数据库连接池创建
 * @author: wuhk
 * @date: 2023/8/21 16:14
 * @version: 1.0
 * @company 航天信息
 **/
@Configuration
public class DataSourceConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource dataSource(){
        return new DruidDataSource();
    }
}
