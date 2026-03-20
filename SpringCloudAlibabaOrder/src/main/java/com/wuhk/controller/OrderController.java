package com.wuhk.controller;

import com.wuhk.entity.OrderSeata;
import com.wuhk.feign.StockFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @className: OrderController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/9 16:12
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    /*@Autowired
    private RestTemplate restTemplate;*/
    //@Autowired
    //private StockFeignService stockFeignService;

    @Value("${user.name}")
    private String name;
    @RequestMapping("/add")
    public String add(){
        log.info("ture");
        //String forObject = restTemplate.getForObject("http://stock/stock/puduct", String.class);
        //String forObject = stockFeignService.puduct();
        return "hello world"+ "forObject" + name;
    }
}
