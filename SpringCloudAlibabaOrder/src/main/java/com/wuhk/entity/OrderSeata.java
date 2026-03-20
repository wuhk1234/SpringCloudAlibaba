package com.wuhk.entity;

import lombok.Data;

/**
 * @className: OrderSeata
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/28 16:13
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class OrderSeata {
    private int id;
    private Integer productId;
    private int totalAmount;
    private int status;
    private String name;
}
