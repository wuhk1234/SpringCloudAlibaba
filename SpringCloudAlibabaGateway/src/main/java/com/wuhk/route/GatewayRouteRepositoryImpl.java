package com.wuhk.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: GatewayRouteRepositoryImpl
 * @description: 7.路由匹配规则实现
 * @author: wuhk
 * @date: 2023/8/22 10:56
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@Component
public class GatewayRouteRepositoryImpl implements GatewayRouteRepository{
    @Autowired
    private GatewayRouteLocator gatewayRouteLocator;
    @Override
    public void saveData(String id, RouteDefinition definition) {

    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public List<RouteDefinition> getAllList() {
        log.info("7.路由匹配规则实现");
        //1. 获取配置文件中的routeList
        //2. 上一步获取的routeList值转化为网关route list
//        return gatewayRouteLocatorList.getGatewayRouteLocator().stream().map(gatewayRouteDefinition -> {
//            try {
//                return GatewayRouteDefintionConvert.toRouteDefinition(gatewayRouteDefinition);
//            }catch (Exception e){
//                log.error("加载动态路由：{}，异常", e);
//                return null;
//            }
//        }).filter(Objects::nonNull).collect(Collectors.toList());

        //1. 获取配置文件中的routeList
        //2. 上一步获取的routeList值转化为网关route list
        return gatewayRouteLocator.getGatewayRouteLocator().stream()
                .map(gatewayRouteDefinition -> {
                    try {
                        //log.info("配置文件的路由信息:{}", gatewayRouteDefinition);
                        return GatewayRouteDefintionConvert.toRouteDefinition(gatewayRouteDefinition);
                    } catch (Exception e) {
                        log.error("加载动态路由：{}，异常", e);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
