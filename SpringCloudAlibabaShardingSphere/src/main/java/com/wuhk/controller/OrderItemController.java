package com.wuhk.controller;

import com.alibaba.fastjson.JSONObject;
import com.wuhk.entity.OrderItem;
import com.wuhk.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @className: OrderItemController
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/5 0005 15:05
 * @version: 1.0
 * @company 航天信息
 **/

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping("/add")
    public long addOne(@RequestBody OrderItem item) {
        Random rand = new Random();
        int randomNumber = rand.nextInt();
        item.setOrderItemId(randomNumber);
        return this.orderItemService.addOne(item);
    }


}
