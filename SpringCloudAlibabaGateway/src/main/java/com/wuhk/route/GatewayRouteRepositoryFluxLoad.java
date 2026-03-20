package com.wuhk.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @className: GatewayRouteRepositoryFluxLoad
 * @description: 9.通过flux加载
 * @author: wuhk
 * @date: 2023/8/22 11:20
 * @version: 1.0
 * @company 航天信息
 **/
@Component
@Slf4j
public class GatewayRouteRepositoryFluxLoad implements RouteDefinitionRepository {
    @Resource
    GatewayRouteRepository gatewayRouteRepository;
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        log.info("通过flux加载: {}",gatewayRouteRepository.getAllList());
        return Flux.fromIterable(gatewayRouteRepository.getAllList());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
