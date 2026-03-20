package com.wuhk.controller;

import com.wuhk.entity.StockSeata;
import com.wuhk.service.StockSeataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: OrderSeataController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/28 16:10
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/stockSeata")
@Slf4j
public class StockSeataController {
    @Autowired
    private StockSeataService stockSeataService;
    @RequestMapping("/add")
    public String creat(){
        StockSeata orderSeata = new StockSeata();
        orderSeata.setProductId(10);
        orderSeata.setCount(2);
        log.info("新增入库成功！");
        stockSeataService.creat(orderSeata);
        return "库存新增成功！！";
    }

    @RequestMapping("/update/{productId}")
    public String update(@PathVariable("productId") Integer productId){
        log.info("分布式事务调用库存服务");
        stockSeataService.update(productId);
        return "库存扣减成功！！";
    }

}
