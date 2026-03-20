package com.wuhk.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuhk.entity.User;
import com.wuhk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: UserController
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:04
 * @version: 1.0
 * @company 航天信息
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/add")
    public long addUser(@RequestBody User user){
        return userService.addOne(user);
    }

    @GetMapping("/test")
    public void testTransactional(){
        this.userService.testTransactional();
    }

}
