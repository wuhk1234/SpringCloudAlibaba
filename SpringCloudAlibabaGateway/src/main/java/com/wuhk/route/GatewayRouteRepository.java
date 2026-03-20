package com.wuhk.route;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * @className: GatewayRouteRepository
 * @description: 6.自定义路由匹配
 * @author: wuhk
 * @date: 2023/8/22 10:55
 * @version: 1.0
 * @company 航天信息
 **/

public interface GatewayRouteRepository {
    /**
     * 保存路由信息
     *
     * @param id
     * @param definition
     * @throws Exception
     */
    void saveData(String id, RouteDefinition definition);

    /**
     * 删除路由信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean remove(String id);

    /**
     * 查询获取所有的路由信息
     *
     * @return
     */
    List<RouteDefinition> getAllList();
}
