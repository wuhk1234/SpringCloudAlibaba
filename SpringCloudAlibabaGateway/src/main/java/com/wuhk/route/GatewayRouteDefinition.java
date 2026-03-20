package com.wuhk.route;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: GatewayRouteDefinition
 * @description: 3.路由规则
 * @author: wuhk
 * @date: 2023/8/22 10:47
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class GatewayRouteDefinition {
    /**
     * 路由id
     */
    private String id;

    /**
     * 路由断言集合配置
     */
    private List<GatewayPredicateDefinition> predicates = new ArrayList<>();

    /**
     * 路由过滤器集合配置
     */
    private List<GatewayFilterDefinition> filters = new ArrayList<>();

    /**
     * 转发目标uri
     */
    private String uri;

    /**
     * 执行顺序
     */
    private int order = 0;
}
