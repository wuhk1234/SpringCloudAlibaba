package com.wuhk.feign.fallback;

import com.wuhk.feign.SentinelSockService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @className: SentinelStockFallback
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/25 15:02
 * @version: 1.0
 * @company 航天信息
 **/
@Component
public class SentinelStockFallback implements SentinelSockService {

    @Override
    public String puduct2() {
        return "降价处理！！！";
    }
}
