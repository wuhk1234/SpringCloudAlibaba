package com.wuhk.service;

import com.wuhk.entity.OrderItem;

import java.util.List;

/**
 * @className: OrderItemService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 16:01
 * @version: 1.0
 * @company 航天信息
 **/

public interface OrderItemService {
    long addOne(OrderItem orderItem);

    List<OrderItem> getByOrderId(int id);

    List<OrderItem> getOrderItemByPrice(int price);
}
