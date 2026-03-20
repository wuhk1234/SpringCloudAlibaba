package com.wuhk.mapper;

import com.wuhk.entity.OrderSeata;

import java.util.List;

/**
 * @className: OrderSeataMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/28 16:16
 * @version: 1.0
 * @company 航天信息
 **/

public interface OrderSeataMapper{
    void creat(OrderSeata orderSeata);
    List<OrderSeata> list();
    OrderSeata orderSeataById(Integer id);
}
