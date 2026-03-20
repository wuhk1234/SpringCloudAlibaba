package com.wuhk.route;

import lombok.Data;

import java.util.List;

/**
 * @className: GatewayRouteLocatorList
 * @description: 2.加载路由规则
 * @author: wuhk
 * @date: 2023/8/22 10:43
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class GatewayRouteLocator {
    private List<GatewayRouteDefinition> gatewayRouteLocator;
}
