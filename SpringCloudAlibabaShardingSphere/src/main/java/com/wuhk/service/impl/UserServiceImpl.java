package com.wuhk.service.impl;

import com.wuhk.entity.User;
import com.wuhk.mapper.UserMapper;
import com.wuhk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: UserServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 16:01
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public long addOne(User user) {
        this.userMapper.addOne(user);
        return user.getUserId();
    }

    @Override
    public void testTransactional() {

    }

    @Override
    public User getOneById(long id) {
        return userMapper.getOneById(id);
    }
}
