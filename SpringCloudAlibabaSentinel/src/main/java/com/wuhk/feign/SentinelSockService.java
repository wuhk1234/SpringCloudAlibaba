package com.wuhk.feign;

import com.wuhk.feign.fallback.SentinelStockFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: SentinelSockService
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/25 14:24
 * @version: 1.0
 * @company 航天信息
 **/
@FeignClient(value = "stock" ,path = "/stock",fallback = SentinelStockFallback.class)
public interface SentinelSockService {
    @RequestMapping("/puduct2")
    String puduct2();
}
