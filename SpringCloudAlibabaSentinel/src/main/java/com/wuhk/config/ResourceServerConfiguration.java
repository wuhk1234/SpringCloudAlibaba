package com.wuhk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @className: ResourceServerConfiguration
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/21 9:50
 * @version: 1.0
 * @company 航天信息
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //对请求的接口做验证
        http.authorizeRequests().antMatchers("/api/order/**").authenticated();
    }
}
