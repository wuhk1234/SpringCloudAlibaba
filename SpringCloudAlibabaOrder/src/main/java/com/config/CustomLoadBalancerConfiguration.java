package com.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @className: CustomLoadBalancerConfiguration
 * @description: 自定义负载均衡器
 * @author: wuhk
 * @date: 2023/3/10 17:26
 * @version: 1.0
 * @company 航天信息
 **/
//@Configuration
//@LoadBalancerClient(value = "stock",configuration = MyRandomLoadBalancer.class)
public class CustomLoadBalancerConfiguration {
    //@Bean
    public ReactorLoadBalancer<ServiceInstance> reactorLoadBalancer(Environment environment,
                                                                    LoadBalancerClientFactory loadBalancerClientFactory){
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new MyRandomLoadBalancer(loadBalancerClientFactory.getProvider(name, ServiceInstanceListSupplier.class),name);
    }
}
