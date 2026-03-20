package com.wuhk.feign.fallback;

import com.wuhk.feign.StockFeignService;
import feign.hystrix.FallbackFactory;

/**
 * @className: StockFallback
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/10 20:26
 * @version: 1.0
 * @company 航天信息
 **/

public class StockFallback implements FallbackFactory<StockFeignService> {
    @Override
    public StockFeignService create(Throwable throwable) {
        return null;
    }
}
