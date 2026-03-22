package com.wuhk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @className: LuaConfiguration
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/22 0022 15:33
 * @version: 1.0
 * @company 航天信息
 **/

@Configuration
public class LuaConfiguration {
    @Bean
    public DefaultRedisScript<Boolean> redisluaAdd() {
        DefaultRedisScript<Boolean> LuaScript = new DefaultRedisScript<>();
        LuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/bf_add.lua")));
        LuaScript.setResultType(Boolean.class);
        return LuaScript;
    }
    @Bean
    public DefaultRedisScript<Boolean> redisluaExist() {
        DefaultRedisScript<Boolean> LuaScript = new DefaultRedisScript<>();
        LuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/bf_exist.lua")));
        LuaScript.setResultType(Boolean.class);
        return LuaScript;
    }
    @Bean
    public DefaultRedisScript<Boolean> redisluaSetNX() {
        DefaultRedisScript<Boolean> LuaScript = new DefaultRedisScript<>();
        LuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/setnx_ex.lua")));
        LuaScript.setResultType(Boolean.class);
        return LuaScript;
    }
    @Bean
    public DefaultRedisScript<Boolean> redisScriptTest() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/Test.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
    @Bean
    public DefaultRedisScript<Boolean> redisScriptExpandLockExpire() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/expand_lock_expire.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
    @Bean
    public DefaultRedisScript<Boolean> redisScriptReleaseLock() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/release_lock.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
