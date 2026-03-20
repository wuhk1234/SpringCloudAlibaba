package com.wuhk.controller;

import com.wuhk.entity.OrderSeata;
import com.wuhk.service.OrderSeataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: OrderSeataController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/28 16:10
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/orderSeata")
public class OrderSeataController {
    @Autowired
    private OrderSeataService orderSeataService;
    @RequestMapping("/add")
    public String creat(){
        OrderSeata orderSeata = new OrderSeata();
        orderSeata.setProductId(10);
        orderSeata.setTotalAmount(2);
        orderSeata.setStatus(0);
        orderSeata.setName("token");
        orderSeataService.creat(orderSeata);
        return "下单成功！！";
    }
    @RequestMapping("/list")
    public List<OrderSeata> list() throws InterruptedException {
        return orderSeataService.list();
    }

    @RequestMapping("/byId/{id}")
    public OrderSeata orderSeataById(@PathVariable Integer id){
        return orderSeataService.orderSeataById(id);
    }
}
