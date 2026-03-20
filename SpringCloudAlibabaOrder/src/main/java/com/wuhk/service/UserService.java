package com.wuhk.service;

import com.wuhk.entity.User;

import java.util.List;

/**
 * @className: UserService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/9 0009 20:00
 * @version: 1.0
 * @company 航天信息
 **/

public interface UserService {
    String cacheData();
    List<User> findList();
    void addUser(User user);
    User getUserById(String userId);
}
