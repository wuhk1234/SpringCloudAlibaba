package com.wuhk.service;

import com.wuhk.entity.Order;

import java.util.List;

/**
 * @className: OrderService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 16:00
 * @version: 1.0
 * @company 航天信息
 **/

public interface OrderService {
    long insertOne(Order order);
    Order selectOne(long orderId, int userId);
    List<Order> getOrderByUserId(long id);
}
