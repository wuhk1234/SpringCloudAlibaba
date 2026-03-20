package com.wuhk.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuhk.entity.Order;
import com.wuhk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @className: OrderController
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:05
 * @version: 1.0
 * @company 航天信息
 **/

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/add")
    public long addOne(@RequestBody Order order){
        Random rand = new Random();
        int randomNumber = rand.nextInt();
        order.setOrderId((long) randomNumber);
        return this.orderService.insertOne(order);
    }

}
