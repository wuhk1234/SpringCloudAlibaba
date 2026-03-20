package com.wuhk.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 认证服务配置适配器
 * 我们需要重写三个方法(不写就是用默认的, 根据需求,一般需要重写)
 *
 * @Override public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
 * 访问安全配置。
 * }
 * @Override public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
 * 决定clientDeatils信息的处理服务
 * }
 * @Override public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
 * 访问端点配置。tokenStroe、tokenEnhancer服务
 * }
 */
@Configuration
@EnableAuthorizationServer //开启认证授权中心
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    /**
     * 身份信息管理类
     */
    @Autowired
    //@Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;


    @Autowired
    //@Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
//        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * clientDetail 信息里的client_secret字段加解密器
     * client_secret密码加密器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置客户端信息
     * 配置从哪里获取ClientDetails信息
     * 在client_credentials授权方式下，只要这个ClientDetails信息。
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * 一般生产环境，将客户端信息存储在内存中
         */
        clients.jdbc(dataSource);
    }

    /**
     * 配置认证服务端点
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).userDetailsService(userDetailsService());
        /**
         * 替换原有的默认授权地址为新的授权地址
         */
//        endpoints.pathMapping("/oauth/token", "/oauth/login");
    }
    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager();
        memoryUserDetailsManager.createUser(User.withUsername("user_1").password(passwordEncoder().encode("123456"))
                .authorities("ROLE_USER").build());
        memoryUserDetailsManager.createUser(User.withUsername("user_2").password(passwordEncoder().encode("1234567"))
                .authorities("ROLE_USER").build());
        return memoryUserDetailsManager;
    }

    /**
     * 配置：安全检查流程,用来配置令牌端点（Token Endpoint）的安全与权限访问
     * 默认过滤器：BasicAuthenticationFilter
     * 1、oauth_client_details表中clientSecret字段加密【ClientDetails属性secret】
     * 2、CheckEndpoint类的接口 oauth/check_token 无需经过过滤器过滤，默认值：denyAll()
     * 对以下的几个端点进行权限配置：
     * /oauth/authorize：授权端点
     * /oauth/token：令牌端点
     * /oauth/confirm_access：用户确认授权提交端点
     * /oauth/error：授权服务错误信息端点
     * /oauth/check_token：用于资源服务访问的令牌解析端点
     * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
     **/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }
    @Bean
    AuthenticationManager authenticationManager(){
        AuthenticationManager authenticationManager = authentication -> authenticationProvider().authenticate(authentication);
        return authenticationManager;
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
