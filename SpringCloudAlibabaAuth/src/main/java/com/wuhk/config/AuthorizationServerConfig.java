package com.wuhk.config;

import com.wuhk.service.impl.UserDatilsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @className: AuthorizationServerConfig
 * @description: 认证服务配置适配器，采用数据库读取方式
 * 我们需要重写三个方法(不写就是用默认的, 根据需求,一般需要重写)
 * @Override public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
 * 访问安全配置。
 * }
 * @Override public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
 * 决定clientDeatils信息的处理服务
 * }
 * @Override public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
 * 访问端点配置。tokenStroe、tokenEnhancer服务
 * }
 * @author: wuhk
 * @date: 2023/8/21 15:36
 * @version: 1.0
 * @company 航天信息
 **/

@Configuration
@EnableAuthorizationServer //开启认证授权中心
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //身份信息管理类
    @Autowired
    private AuthenticationManager authenticationManager;

    //创建数据库连接
    @Autowired
    private DataSource dataSource;

    /**
     * <p>
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
     * </p>
     * @method AuthorizationServerConfig.configure()
     * @param security
     * @return
     * @author wuhk
     * @date 2023/8/21 15:40
     **/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * <p>第一步：
     *     决定clientDeatils信息的处理服务配置客户端信息
     *    配置从哪里获取ClientDetails信息
     *    在client_credentials授权方式下，只要这个ClientDetails信息。
     * </p>
     * @method AuthorizationServerConfig.configure()
     * @param clients
     * @return
     * @author wuhk
     * @date 2023/8/21 16:02
     **/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * <p>设置token类型，访问端点配置</p>
     * @method AuthorizationServerConfig.configure()
     * @param endpoints
     * @return
     * @author wuhk
     * @date 2023/8/21 16:40
     **/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).userDetailsService(userDetailsService());
    }

    /**
     * <p>初始化数据库</p>
     * @method AuthorizationServerConfig.tokenStore()
     * @param
     * @return TokenStore
     * @author wuhk
     * @date 2023/8/21 15:47
     **/
    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    /**
     * <p>
     *    clientDetail 信息里的client_secret字段加解密器
     *    client_secret密码加密器
     * </p>
     * @method AuthorizationServerConfig.passwordEncoder()
     * @param
     * @return PasswordEncoder
     * @author wuhk
     * @date 2023/8/21 15:49
     **/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * <p>身份信息管理类</p>
     * @method AuthorizationServerConfig.authenticationManager()
     * @param
     * @return AuthenticationManager
     * @author wuhk
     * @date 2023/8/21 16:09
     **/
    @Bean
    public AuthenticationManager authenticationManager(){
        AuthenticationManager authenticationManager = authentication -> authenticationProvider().authenticate(authentication);
        return authenticationManager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    /**
     * <p>加载用户信息</p>
     * @method AuthorizationServerConfig.userDetailsService()
     * @param
     * @return UserDetailsService
     * @author wuhk
     * @date 2023/8/21 16:06
     **/
    @Bean
    public UserDetailsService userDetailsService() {
        /*InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("wuhk").password(passwordEncoder().encode("123456"))
                .authorities("ROLE_USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("wuhk_1").password(passwordEncoder().encode("1234567"))
                .authorities("ROLE_USER").build());*/
        return new UserDatilsServiceImpl();
    }
}
