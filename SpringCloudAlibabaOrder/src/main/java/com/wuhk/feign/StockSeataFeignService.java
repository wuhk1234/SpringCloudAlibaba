package com.wuhk.feign;

import com.wuhk.feign.fallback.StockSeataFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: StockSeataFeignService
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/29 1:09
 * @version: 1.0
 * @company 航天信息
 **/
@FeignClient(name = "stock",fallbackFactory = StockSeataFallback.class)
public interface StockSeataFeignService {
    @RequestMapping("/stockSeata/update/{productId}")
    String update(@PathVariable("productId") Integer productId);
}
