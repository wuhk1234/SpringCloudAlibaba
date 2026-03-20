package com.wuhk.controller;

import com.wuhk.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: RedisController
 * @description: redis控制器
 * @author: wuhk
 * @date: 2023/6/9 10:36
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisUtil redisUtil;
    @RequestMapping("/set/{val}")
    public String setVal(@PathVariable("val") String val){
        redisUtil.set(val,val);
        return "success";
    }

    @RequestMapping("/get")
    public String getVal(String val){
        String get = (String) redisUtil.get(val);
        return get;
    }
}
