package com.wuhk.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className: GatewayRouteConfig
 * @description: 1.动态路由加载
 * @author: wuhk
 * @date: 2023/8/22 10:41
 * @version: 1.0
 * @company 航天信息
 **/
@Configuration
@RefreshScope
@EnableConfigurationProperties
@Slf4j
public class GatewayRouteConfig {
    /**
     * <p>网关启动加载路由规则集合</p>
     * @method GatewayRouteConfig.gatewayRouteLocatorList()
     * @param
     * @return GatewayRouteLocatorList：路由规则集合
     * @author wuhk
     * @date 2023/8/22 10:46
     **/
    @Bean
    @ConfigurationProperties(prefix = "locator.routes")
    public GatewayRouteLocator gatewayRouteLocator(){
        log.info("1动态路由加载");
        return new GatewayRouteLocator();
    }
}
