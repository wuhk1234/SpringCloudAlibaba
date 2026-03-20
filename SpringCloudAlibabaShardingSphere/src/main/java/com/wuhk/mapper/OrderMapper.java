package com.wuhk.mapper;

import com.wuhk.entity.Order;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @className: OrderMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 17:26
 * @version: 1.0
 * @company 航天信息
 **/

public interface OrderMapper {
    void insertOne(Order order);
    Order selectOne(@Param("orderId") long orderId, @Param("userId") int userId);
    List<Order> getOrderByUserId(long id);
}
