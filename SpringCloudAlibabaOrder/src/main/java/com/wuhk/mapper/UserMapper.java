package com.wuhk.mapper;

import com.wuhk.entity.User;

import java.util.List;

/**
 * @className: UserMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/9 0009 19:58
 * @version: 1.0
 * @company 航天信息
 **/

public interface UserMapper {
    List<User> findList();
    User getFindUserId(String userId);
    void addUser(User user);
}
