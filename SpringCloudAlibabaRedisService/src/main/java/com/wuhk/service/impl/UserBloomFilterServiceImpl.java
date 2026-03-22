/*
package com.wuhk.service.impl;

import com.wuhk.config.LuaConfiguration;
import com.wuhk.entity.UserScore;
import com.wuhk.mapper.UserScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * @className: UserBloomFilterServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 19:33
 * @version: 1.0
 * @company 航天信息
 **//*

@Service
public class UserBloomFilterServiceImpl {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LuaConfiguration luaConfiguration;
    @Autowired
    private UserScoreMapper userScoreMapper;

    public static final String BLOOMFILTER_NAME = "user_bloom";

    */
/**
     * 程序启动时初始化用户标识
     *//*

    @PostConstruct
    public void initUserBloomFilter(){
        System.out.println("initUserBloomFilter start...");
        List<UserScore> userScores = userScoreMapper.selectByExample();
        if(!CollectionUtils.isEmpty(userScores)){
            userScores.forEach(userScore -> bloomFilterAdd(userScore.getUserId()));
        }
        System.out.println("initUserBloomFilter end...");
    }


    */
/**
     * 向布隆过滤器中添加元素
     * @param uid
     * @return
     *//*

    public Boolean bloomFilterAdd(String uid){
        //封装传递脚本参数
        ArrayList<Object> params = new ArrayList<>();
        params.add(BLOOMFILTER_NAME);
        params.add(uid);
        return (Boolean) redisTemplate.execute(luaConfiguration.redisluaAdd(), params);
    }

    */
/**
     * 检验元素是否可能存在于布隆过滤器中
     * @param uid
     * @return
     *//*

    public Boolean bloomFilterExist(String uid){
        //封装传递脚本参数
        ArrayList<Object> params = new ArrayList<>();
        params.add(BLOOMFILTER_NAME);
        params.add(uid);
        return (Boolean) redisTemplate.execute(luaConfiguration.redisluaExist(), params);
    }
}
*/
