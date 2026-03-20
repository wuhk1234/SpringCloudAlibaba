package com.wuhk.service.impl;

import com.wuhk.entity.Order;
import com.wuhk.mapper.OrderMapper;
import com.wuhk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: OrderServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 16:01
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public long insertOne(Order order) {
        this.orderMapper.insertOne(order);
        return order.getOrderId();
    }

    @Override
    public Order selectOne(long orderId, int userId) {
        return orderMapper.selectOne(orderId,userId);
    }

    @Override
    public List<Order> getOrderByUserId(long id) {
        return orderMapper.getOrderByUserId(id);
    }
}
