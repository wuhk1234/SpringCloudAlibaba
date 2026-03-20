package com.wuhk.mapper;

import com.wuhk.entry.UserInfo;

/**
 * @className: UserMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/21 16:49
 * @version: 1.0
 * @company 航天信息
 **/

public interface UserMapper {
    UserInfo selectOne(String username);
}
