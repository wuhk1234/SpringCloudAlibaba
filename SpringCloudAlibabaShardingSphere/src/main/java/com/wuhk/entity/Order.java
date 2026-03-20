package com.wuhk.entity;

import lombok.Data;

import java.util.Date;

/**
 * @className: Order
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:56
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class Order {
    private long orderId;
    private long userId;
    private Date createTime;
    private long totalPrice;
}
