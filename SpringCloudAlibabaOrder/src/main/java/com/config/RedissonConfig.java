package com.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @className: RedissonConfig
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/9 0009 21:24
 * @version: 1.0
 * @company 航天信息
 **/

@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonClient redissonClient() throws Exception{
        Config config = Config.fromYAML(redissonProperties.getConfig());
        Redisson.create(config);
        System.out.println("Redisson 已启动");
        return Redisson.create(config);
    }
}
