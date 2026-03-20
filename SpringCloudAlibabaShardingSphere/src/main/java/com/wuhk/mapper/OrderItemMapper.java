package com.wuhk.mapper;

import com.wuhk.entity.OrderItem;

import java.util.List;

/**
 * @className: OrderItemMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 17:27
 * @version: 1.0
 * @company 航天信息
 **/

public interface OrderItemMapper {
    void addOne(OrderItem orderItem);

    List<OrderItem> getByOrderId(int id);

    List<OrderItem> getOrderItemByPrice(int price);
}
