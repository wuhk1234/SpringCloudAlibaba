package com.wuhk.service;

import com.wuhk.entity.OrderSeata;

import java.util.List;

/**
 * @className: OrderSeataService
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/28 16:12
 * @version: 1.0
 * @company 航天信息
 **/

public interface OrderSeataService {
    void creat(OrderSeata orderSeata);
    List<OrderSeata> list() throws InterruptedException;
    OrderSeata orderSeataById(Integer id);
}
