package com.wuhk.auth.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


import javax.sql.DataSource;

@Configuration
public class DataSource1Config {

    @Bean(name = "db1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource db1DataSource() {
        return new DruidDataSource();
    }



}
