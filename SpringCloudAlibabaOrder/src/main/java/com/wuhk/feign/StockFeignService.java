package com.wuhk.feign;

import com.config.FeginConfig;
import com.wuhk.feign.fallback.StockFallback;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @className: StockFeignService
 * @description: 库存服务feign接口调用
 * @author: wuhk
 * @date: 2023/3/10 20:24
 * @version: 1.0
 * @company 航天信息
 **/
//@FeignClient(name = "stock",fallbackFactory = StockFallback.class,configuration = FeginConfig.class)
public interface StockFeignService {
    //@PostMapping("/stock/puduct")
    @RequestLine("GET //stock/puduct") //契约配置时需要变为feign的接口传参方式
    public String puduct();
}
