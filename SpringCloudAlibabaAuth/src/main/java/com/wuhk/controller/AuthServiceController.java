package com.wuhk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: AuthServiceController
 * @description: TODO
 * @author: wuhk
 * @date: 2025/4/14 0014 22:03
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/test")
public class AuthServiceController {
    @RequestMapping("/check")
    public String heathCheck(){
        return "通";
    }
}
