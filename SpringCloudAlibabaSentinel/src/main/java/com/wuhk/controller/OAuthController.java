package com.wuhk.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @className: OAuthController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/21 9:55
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/api/order")
public class OAuthController {
    @RequestMapping("/add")
    public String add(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println("true = " + true);
        return "hello world"+ new Random().nextInt(12);
    }
}
