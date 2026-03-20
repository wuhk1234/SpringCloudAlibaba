package com.wuhk.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: HealthController
 * @description: 健康检查
 * @author: wuhk
 * @date: 2023/11/6 20:27
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/Health")
public class HealthController {
    @RequestMapping("/check")
    public String check() {
        return "成功！！";
    }
}
