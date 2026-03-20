package com.wuhk.controller;

import com.wuhk.entity.User;
import com.wuhk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @className: UserController
 * @description: 布隆过滤器 + Redis业务场景
 * @author: wuhk
 * @date: 2026/3/9 0009 20:35
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/cacheData")
    public ResponseEntity<String> cacheData() {
        // 应用启动时，将数据库中的有效用户ID预热到布隆过滤器
        String result = userService.cacheData();
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
       User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("用户不存在");
        }
        return ResponseEntity.ok(user);
    }
    @RequestMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("用户创建成功");
    }
}
