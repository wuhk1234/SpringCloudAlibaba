package com.wuhk.entity;

import lombok.Data;

/**
 * @className: User
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/9 0009 19:54
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class User {
    private String id;
    private String userName;
    private String password;
    private String userRole;
}
