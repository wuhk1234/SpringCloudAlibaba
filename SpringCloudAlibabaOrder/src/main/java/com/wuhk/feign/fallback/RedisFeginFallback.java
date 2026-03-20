package com.wuhk.feign.fallback;

import com.wuhk.feign.RedisFeginService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Service;

/**
 * @className: RedisFeginFallback
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/9 14:17
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class RedisFeginFallback implements FallbackFactory<RedisFeginService> {
    @Override
    public RedisFeginService create(Throwable throwable) {
        return val -> "调用redis成功！！";
    }
}
