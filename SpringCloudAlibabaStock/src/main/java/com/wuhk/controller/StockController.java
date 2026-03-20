package com.wuhk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: StockController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/9 16:13
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/stock")
public class StockController {
    @Value("${server.port}")
    private String port;
    @RequestMapping("/puduct")
    public String puduct() throws InterruptedException {
        System.out.println("库存减少");
        Thread.sleep(4000);
        return "hello world:库存减少 !" + port;
    }


    /**
     * <p>Sentinel的远程调用降级</p>
     * @method StockController.java()
     * @param
     * @return String
     * @author wuhk
     * @date 2023/3/25 14:25
     **/
    @RequestMapping("/puduct2")
    public String puduct2() {
        System.out.println("Sentinel的远程调用降级");
        int a = 1/0;
        return "Sentinel的远程调用降级！！！" + port;
    }
}
