package com.wuhk.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @className: BloomFilterConfig
 * @description: 布隆过滤器配置类
 * @author: wuhk
 * @date: 2026/3/9 0009 19:47
 * @version: 1.0
 * @company 航天信息
 **/
@Configuration
public class BloomFilterConfig {
    @Bean
    public RBloomFilter<String> userBloomFilter(RedissonClient redissonClient) {
        // 获取或创建布隆过滤器
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("userBloomFilter");
        // 初始化：预计元素数量100万，误判率0.1%
        bloomFilter.tryInit(1000000L, 0.001);
        return bloomFilter;
    }
    @Bean
    public RBloomFilter<Long> productBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<Long> bloomFilter = redissonClient.getBloomFilter("productBloomFilter");
        bloomFilter.tryInit(500000L, 0.001);  // 50万商品，误判率0.1%
        return bloomFilter;
    }
}
