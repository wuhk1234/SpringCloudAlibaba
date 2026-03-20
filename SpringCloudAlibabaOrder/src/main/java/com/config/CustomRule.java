package com.config;

/*import com.alibaba.nacos.client.naming.utils.ThreadLocalRandom;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.util.CollectionUtils;*/

import java.util.List;

/**
 * @className: CustomRule
 * @description: 自定义负载均衡器随机算法
 * @author: wuhk
 * @date: 2023/3/10 16:30
 * @version: 1.0
 * @company 航天信息
 **/

/*public class CustomRule extends AbstractLoadBalancerRule {
    public Server choose(Object key) {
        //获取当前的实例
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        List<Server> serverList = loadBalancer.getReachableServers();
        *//*if (CollectionUtils.isEmpty(serverList)) {
            //LOGGER.warn("no instance in service {}", serverList);
            return null;
        }*//*
        int random = ThreadLocalRandom.current().nextInt(serverList.size());
        Server server = serverList.get(random);
        //判断是否有存活的
        *//*if(server.isAlive()){
            return null;
        }*//*
        return server;
    }

    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}*/
