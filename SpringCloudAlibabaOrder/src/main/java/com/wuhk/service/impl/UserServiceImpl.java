package com.wuhk.service.impl;

import com.wuhk.entity.User;
import com.wuhk.mapper.UserMapper;
import com.wuhk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @className: UserServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/9 0009 20:01
 * @version: 1.0
 * @company 航天信息
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    //1.创建rBloomFilter所加载的Bean，否则会报：a bean of type 'org.redisson.api.RBloomFilter' that could not be found.
    //2.OrderApplication启动器中@ComponentScan(basePackages = "com.wuhk.**")必须扫描到BloomFilterConfig类中所加载的Bean
    @Autowired
    private RBloomFilter<String> rBloomFilter;
    //1.创建redisTemplate所加载的Bean，否则会报：a bean of type 'org.springframework.data.redis.core.RedisTemplate' in your configuration.
    //2.OrderApplication启动器中@ComponentScan(basePackages = "com.wuhk.**")必须扫描到RedisConfig类中所加载的Bean
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    // 缓存过期时间
    private static final long CACHE_EXPIRE_SECONDS = 30 * 60;  // 30分钟

    @Override
    public String cacheData() {
        List<User> list = userMapper.findList();
        for (User user : list) {
            rBloomFilter.add(user.getId());
        }
        return "缓存完成！";
    }

    @Override
    public List<User> findList() {
        return userMapper.findList();
    }
    /**
     * 新增用户时，同步更新布隆过滤器
     */
    @Override
    public void addUser(User user) {
        // 1. 保存到数据库
        userMapper.addUser(user);
        // 2. 添加到布隆过滤器
        rBloomFilter.add(user.getId());
        // 3. 清除Redis缓存（如有）
        redisTemplate.delete("user:"+user.getId());
    }

    /**
     * 三级防护：布隆过滤器 → Redis缓存 → 数据库
     */
    @Override
    public User getUserById(String userId){
        if (!rBloomFilter.contains(userId)){
            // 布隆过滤器判定一定不存在，直接返回，避免穿透数据库
            log.warn("布隆过滤器拦截无效用户ID: {}", userId);
            return null;
        }
        // 2. 第二级：查询Redis缓存
        String cacheKey = "user:" + userId;
        User user = (User) redisTemplate.opsForValue().get(cacheKey);
        if (user != null) {
            return user;  // 缓存命中，直接返回
        }
        // 3. 第三级：查询数据库
        user =userMapper.getFindUserId(userId);
        if (user != null) {
            // 数据库存在，写入Redis缓存
            redisTemplate.opsForValue().set(cacheKey,user,CACHE_EXPIRE_SECONDS, TimeUnit.MINUTES);
        }else {
            // 数据库不存在，缓存空值（短期），防止缓存穿透
            // 注意：这种情况是布隆过滤器误判，实际数据不存在
            redisTemplate.opsForValue().set(cacheKey,new User(),CACHE_EXPIRE_SECONDS,TimeUnit.MINUTES);
        }
        return user;
    }

}
