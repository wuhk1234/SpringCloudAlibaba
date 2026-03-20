package com.wuhk.service.impl;

import com.wuhk.entity.OrderItem;
import com.wuhk.mapper.OrderItemMapper;
import com.wuhk.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: OrderItemServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 16:01
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public long addOne(OrderItem item) {
        this.orderItemMapper.addOne(item);
        return item.getOrderItemId();
    }

    @Override
    public List<OrderItem> getByOrderId(int id) {
        return orderItemMapper.getByOrderId(id);
    }

    @Override
    public List<OrderItem> getOrderItemByPrice(int price) {
        return orderItemMapper.getOrderItemByPrice(price);
    }
}
