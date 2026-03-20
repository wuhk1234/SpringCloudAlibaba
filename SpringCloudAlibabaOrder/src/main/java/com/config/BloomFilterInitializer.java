package com.config;

import com.wuhk.entity.User;
import com.wuhk.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: BloomFilterInitializer
 * @description: 数据预热服务
 * @author: wuhk
 * @date: 2026/3/9 0009 19:49
 * @version: 1.0
 * @company 航天信息
 **/
@Component
public class BloomFilterInitializer implements CommandLineRunner {
    @Autowired
    private RBloomFilter<String> userBloomFilter;
    //查数据库
    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) {
        // 应用启动时，将数据库中的有效用户ID预热到布隆过滤器
        List<User> list = userService.findList();
        for (User user : list) {
            userBloomFilter.add(user.getId());
        }
        System.out.println("布隆过滤器预热完成，已添加 " + list.size() + " 个用户ID");
    }
}
