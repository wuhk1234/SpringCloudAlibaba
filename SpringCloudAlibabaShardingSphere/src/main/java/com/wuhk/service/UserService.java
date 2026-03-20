package com.wuhk.service;

import com.wuhk.entity.User;

/**
 * @className: UserService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 16:01
 * @version: 1.0
 * @company 航天信息
 **/

public interface UserService {
    long addOne(User user);
    void testTransactional();
    User getOneById(long id);
}
