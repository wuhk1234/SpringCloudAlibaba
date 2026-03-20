package com.wuhk.feign.fallback;

import com.wuhk.feign.StockSeataFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Service;

/**
 * @className: StockSeataFallback
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/29 1:10
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class StockSeataFallback implements FallbackFactory<StockSeataFeignService> {

    @Override
    public StockSeataFeignService create(Throwable throwable) {
        return productId -> "扣减库存远程调用！";
    }
}
