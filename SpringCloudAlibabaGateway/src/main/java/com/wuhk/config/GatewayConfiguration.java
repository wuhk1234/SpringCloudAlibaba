package com.wuhk.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.wuhk.handler.SentinelExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @className: GatewayConfig
 * @description: 自定义sentinel流控异常返回
 * @author: wuhk
 * @date: 2023/3/31 21:05
 * @version: 1.0
 * @company 航天信息
 **/
@Configuration
public class GatewayConfiguration {
    @Autowired
    private InetUtils inetUtils;
    @PostConstruct
    public void doInit() {
        GatewayCallbackManager.setBlockHandler(new SentinelExceptionHandler());
        //设置监控ip(多网卡时默认获取有问题，所以需要采用springCloud网卡工具类)
        SentinelConfig.setConfig(TransportConfig.HEARTBEAT_CLIENT_IP, inetUtils.findFirstNonLoopbackAddress().getHostAddress());
    }

}

