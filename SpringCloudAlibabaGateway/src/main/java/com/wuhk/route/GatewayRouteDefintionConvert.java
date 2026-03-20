package com.wuhk.route;

import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: GatewayRouteDefintionConvert
 * @description: 8.路由規則设置
 * @author: wuhk
 * @date: 2023/8/22 11:01
 * @version: 1.0
 * @company 航天信息
 **/
@NoArgsConstructor
public class GatewayRouteDefintionConvert {
    /**
     * <p>把传递进来的参数转换成路由对象</p>
     * @method GatewayRouteDefintionConvert.toRouteDefinition()
     * @param gatewayRouteDefinition:路由规则
     * @return RouteDefinition
     * @author wuhk
     * @date 2023/8/22 11:12
     **/
    public static RouteDefinition toRouteDefinition(GatewayRouteDefinition gatewayRouteDefinition){
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRouteDefinition.getId());
        routeDefinition.setOrder(gatewayRouteDefinition.getOrder());

        //设置断言
        List<PredicateDefinition> pdList = new ArrayList<>();
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = gatewayRouteDefinition.getPredicates();
        for (GatewayPredicateDefinition gatewayPredicateDefinition : gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gatewayPredicateDefinition.getArgs());
            predicate.setName(gatewayPredicateDefinition.getName());
            pdList.add(predicate);
        }
        routeDefinition.setPredicates(pdList);

        //设置过滤器
        List<FilterDefinition> filters = new ArrayList<>();
        List<GatewayFilterDefinition> gatewayFilters = gatewayRouteDefinition.getFilters();
        for (GatewayFilterDefinition filterDefinition : gatewayFilters) {
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        routeDefinition.setFilters(filters);
        //判断路径
        URI uri = null;
        if (gatewayRouteDefinition.getUri().startsWith("http")){
            uri = UriComponentsBuilder.fromHttpUrl(gatewayRouteDefinition.getUri()).build().toUri();
        }else {
            // uri为 lb://consumer-service 时使用下面的方法
            uri = URI.create(gatewayRouteDefinition.getUri());
        }
        routeDefinition.setUri(uri);

        return routeDefinition;
    }
}
