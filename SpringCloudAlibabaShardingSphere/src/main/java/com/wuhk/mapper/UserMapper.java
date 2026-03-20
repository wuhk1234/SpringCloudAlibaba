package com.wuhk.mapper;

import com.wuhk.entity.User;

/**
 * @className: UserMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 17:28
 * @version: 1.0
 * @company 航天信息
 **/

public interface UserMapper {
    void addOne(User user);
    User getOneById(long id);
}
