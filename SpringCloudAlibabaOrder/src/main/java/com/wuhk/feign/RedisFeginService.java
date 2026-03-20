package com.wuhk.feign;

import com.wuhk.feign.fallback.RedisFeginFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: RedisFeginService
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/9 14:16
 * @version: 1.0
 * @company 航天信息
 **/
@FeignClient(name = "redis-service",fallbackFactory = RedisFeginFallback.class)
public interface RedisFeginService {
    @RequestMapping("/redis/set/{val}")
    String set(@PathVariable("val") String val);
}
