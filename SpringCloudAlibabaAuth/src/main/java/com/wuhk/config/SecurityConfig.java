package com.wuhk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @className: SecurityConfig
 * @description: 安全拦截机制
 * @author: wuhk
 * @date: 2023/8/21 16:17
 * @version: 1.0
 * @company 航天信息
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * <p>安全拦截机制</p>
     * @method SecurityConfig.configure()
     * @param http
     * @return
     * @author wuhk
     * @date 2023/8/21 16:21
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();
    }

}
