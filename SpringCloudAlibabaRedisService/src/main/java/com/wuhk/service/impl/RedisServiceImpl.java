package com.wuhk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * @className: RedisServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 19:36
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class RedisServiceImpl {
    @Autowired
    private RedisTemplate redisTemplate;

    private static double size = Math.pow(2, 32);

    private DefaultRedisScript<Boolean> lockLuaScript;

    /**
     * 读取缓存
     * @param key
     * @param offset
     * @return
     */
    public boolean getBit(String key, long offset) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.getBit(key, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存(String)
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置失效时间
     * @param key
     * @param value
     * @return
     */
    public boolean setExpire(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的key
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除对应的key
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的key
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存(String)
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 设置过期时间
     * @param key
     * @return
     */
    public Boolean expire(String key,Long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 减少decr by
     * @param key
     * @param value
     * @return
     */
    public boolean decr(final String key, int value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.increment(key,-value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 执行lua脚本
     * @param key
     * @param value
     * @return
     */
    public Boolean luaScript(String key,String value,Long expire){
        lockLuaScript = new DefaultRedisScript<Boolean>();
        lockLuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/setnx_ex.lua")));
        lockLuaScript.setResultType(Boolean.class);
        //封装传递脚本参数
        ArrayList<Object> params = new ArrayList<>();
        params.add(key);
        params.add(value);
        params.add(String.valueOf(expire));
        return (Boolean) redisTemplate.execute(lockLuaScript, params);
    }

    /**
     * 执行lua脚本(释放锁)
     * @param key
     * @param value
     * @return
     */
    public Boolean luaScriptReleaseLock(String key,String value){
        lockLuaScript = new DefaultRedisScript<Boolean>();
        lockLuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/release_lock.lua")));
        lockLuaScript.setResultType(Boolean.class);
        //封装传递脚本参数
        ArrayList<Object> params = new ArrayList<>();
        params.add(key);
        params.add(value);
        return (Boolean) redisTemplate.execute(lockLuaScript, params);
    }

    /**
     * 执行lua脚本(锁续时)
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public Boolean luaScriptExpandLockExpire(String key,String value,Long expire){
        lockLuaScript = new DefaultRedisScript<Boolean>();
        lockLuaScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/expand_lock_expire.lua")));
        lockLuaScript.setResultType(Boolean.class);
        //封装传递脚本参数
        ArrayList<Object> params = new ArrayList<>();
        params.add(key);
        params.add(value);
        params.add(String.valueOf(expire));
        return (Boolean) redisTemplate.execute(lockLuaScript, params);
    }

    /**
     * 获取本机内网IP地址方法
     * @return
     */
    public static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
