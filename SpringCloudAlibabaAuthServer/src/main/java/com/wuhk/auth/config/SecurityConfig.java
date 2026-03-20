package com.wuhk.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @className: SecurityConfig
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/19 16:08
 * @version: 1.0
 * @company 航天信息
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //认证管理器
    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

    /**
     * 安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").fullyAuthenticated().and().httpBasic();
        /*http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*", "/css/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .disable();*/
    }
}
