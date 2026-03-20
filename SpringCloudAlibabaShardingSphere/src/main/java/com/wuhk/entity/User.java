package com.wuhk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @className: User
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:56
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@AllArgsConstructor
public class User {
    private long userId;
    private String name;
    private int age;
}
