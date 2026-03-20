package com.wuhk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: ShardingSphereTestControler
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 13:53
 * @version: 1.0
 * @company 航天信息
 **/
@RequestMapping("/shardingSphere")
@RestController
public class ShardingSphereTestControler {
    @RequestMapping("/shardingSphereTEST")
    public String ShardingSphereTest(){
        return "测试成功！";
    }
}
