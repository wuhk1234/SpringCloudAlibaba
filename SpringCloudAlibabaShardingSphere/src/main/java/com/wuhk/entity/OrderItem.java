package com.wuhk.entity;

import lombok.Data;

/**
 * @className: OrderItem
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:56
 * @version: 1.0
 * @company 航天信息
 **/

@Data
public class OrderItem {

    private long userId;
    private long orderItemId;
    private long orderId;
    private String name;
    private long price;

}
