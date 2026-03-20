package com.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.loadbalancer.core.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: RandomLoadBalancer
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/10 19:10
 * @version: 1.0
 * @company 航天信息
 **/

public class MyRandomLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private static final Log log = LogFactory.getLog(RoundRobinLoadBalancer.class);

    private final AtomicInteger position;

    @Deprecated
    private ObjectProvider<ServiceInstanceSupplier> serviceInstanceSupplier;

    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private final String serviceId;

    /**
     * @param serviceId id of the service for which to choose an instance
     * @param serviceInstanceSupplier a provider of {@link ServiceInstanceSupplier} that
     * will be used to get available instances
     * @deprecated Use {@link #MyRandomLoadBalancer(ObjectProvider, String)}} instead.
     */
    @Deprecated
    public MyRandomLoadBalancer(String serviceId,
                                  ObjectProvider<ServiceInstanceSupplier> serviceInstanceSupplier) {
        this(serviceId, serviceInstanceSupplier, new Random().nextInt(1000));
    }

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     * {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId id of the service for which to choose an instance
     */
    public MyRandomLoadBalancer(
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            String serviceId) {
        this(serviceInstanceListSupplierProvider, serviceId, new Random().nextInt(1000));
    }

    /**
     * @param serviceInstanceListSupplierProvider a provider of
     * {@link ServiceInstanceListSupplier} that will be used to get available instances
     * @param serviceId id of the service for which to choose an instance
     * @param seedPosition Round Robin element position marker
     */
    public MyRandomLoadBalancer(
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            String serviceId, int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.position = new AtomicInteger(seedPosition);
    }

    /**
     * @param serviceId id of the service for which to choose an instance
     * @param serviceInstanceSupplier a provider of {@link ServiceInstanceSupplier} that
     * will be used to get available instances
     * @param seedPosition Round Robin element position marker
     * @deprecated Use {@link #RoundRobinLoadBalancer(ObjectProvider, String, int)}}
     * instead.
     */
    @Deprecated
    public MyRandomLoadBalancer(String serviceId,
                                  ObjectProvider<ServiceInstanceSupplier> serviceInstanceSupplier,
                                  int seedPosition) {
        this.serviceId = serviceId;
        this.serviceInstanceSupplier = serviceInstanceSupplier;
        this.position = new AtomicInteger(seedPosition);
    }

    @SuppressWarnings("rawtypes")
    @Override
    // see original
    // https://github.com/Netflix/ocelli/blob/master/ocelli-core/
    // src/main/java/netflix/ocelli/loadbalancer/RoundRobinLoadBalancer.java
    public Mono<Response<ServiceInstance>> choose(Request request) {
        // TODO: move supplier to Request?
        // Temporary conditional logic till deprecated members are removed.
        if (serviceInstanceListSupplierProvider != null) {
            ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                    .getIfAvailable(NoopServiceInstanceListSupplier::new);
            return supplier.get().next().map(this::getInstanceResponse);
        }
        ServiceInstanceSupplier supplier = this.serviceInstanceSupplier
                .getIfAvailable(NoopServiceInstanceSupplier::new);
        return supplier.get().collectList().map(this::getInstanceResponse);
    }

    private Response<ServiceInstance> getInstanceResponse(
            List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            log.warn("No servers available for service: " + this.serviceId);
            return new EmptyResponse();
        }
        // TODO: enforce order?
        int pos = Math.abs(this.position.incrementAndGet());

        ServiceInstance instance = instances.get(pos % instances.size());

        return new DefaultResponse(instance);
    }

}
