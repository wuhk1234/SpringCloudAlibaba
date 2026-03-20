package com.wuhk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: SchoolStudent
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/21 10:07
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolStudent {
    private int id;
    private String name;
    private String sex;
    private int age;

}
