package com.wuhk.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wuhk.feign.SentinelSockService;
import com.wuhk.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @className: SentienlController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/14 15:02
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@RequestMapping("/sentinel")
public class SentienlController {
    @Autowired
    private SentinelSockService sentinelSockService;
    @Autowired
    private SentinelService sentinelService;
    @Value("${user.name}")
    private String name;
    @RequestMapping("/add")
    //根据QPS流控
    //@SentinelResource(value = "add",blockHandler = "addBlockHandler")
    public String add(){
        System.out.println("true = " + true);
        return "hello world"+ name+ new Random().nextInt(12);
    }

    public String addBlockHandler(BlockException e){
        e.printStackTrace();
        return "add方法被流控了！！" + e;
    }

    @RequestMapping("/addThread")
    //根据线程流控
    @SentinelResource(value = "addThread",blockHandler = "addBlockHandler")
    public String addThread() throws InterruptedException {
        System.out.println("true = " + true);
        Thread.sleep(5000);
        return "hello world"+ name +"："+ new Random().nextInt(1000);
    }


    /**
     * <p>生产订单要对查询订单关联流控：那么资源名称是查询订单(get)，关联资源是生产订单(addOrder)</p>
     * @method SentienlController.addOrder()
     * @param
     * @return String
     * @author wuhk
     * @date 2023/3/15 20:15
     **/
    @RequestMapping("/addOrder")
    public String addOrder() {
        System.out.println("生产订单!!");
        return "生产订单！！"+ name ;
    }
    /**
     * <p>生产订单要对查询订单关联流控：那么资源名称是查询订单(get)，关联资源是生产订单(addOrder)</p>
     * @method SentienlController.get()
     * @param
     * @return String
     * @author wuhk
     * @date 2023/3/15 20:16
     **/
    @RequestMapping("/get")
    public String get() {
        System.out.println("查询订单!!");
        return "查询订单！！"+ name ;
    }

    @RequestMapping("/test1")
    public String test1() {
        System.out.println("生产订单!!");
        return "生产订单！！"+ name +sentinelService.getUser();

    }

    @RequestMapping("/test2")
    public String test2() {
        System.out.println("查询订单信息!!");
        return "查询订单信息！！"+ name +sentinelService.getUser();
    }

    /**
     * <p>Sentienl熔断降级处理：慢调用比例</p>
     * @method SentienlController.java()
     * @param
     * @return String
     * @author wuhk
     * @date 2023/3/24 17:16
     **/
    @RequestMapping("/flowThread")
    public String flowThread() throws InterruptedException {
        System.out.println("true = " + true);
        Thread.sleep(2000);
        return "休眠两秒"+ name +"："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    /**
     * <p>Sentienl熔断降级处理：异常比例</p>
     * @method SentienlController.java()
     * @param
     * @return String
     * @author wuhk
     * @date 2023/3/24 17:17
     **/
    @RequestMapping("/err")
    public String err(){

        int a = 1/0;
        return "Sentienl熔断降级处理：异常比例"+ name +"："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * <p>Sentinel的远程调用降级</p>
     * @method SentienlController.puduct2()
     * @param
     * @return String
     * @author wuhk
     * @date 2023/3/25 14:31
     **/
    @RequestMapping("/puduct2")
    public String puduct2(){
        String ss = sentinelSockService.puduct2();
        return "Sentienl的远程调用降级"+ name +"："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ss;
    }

    /**
     * <p>sentinel根据热点参数来进行流控</p>
     * @method SentienlController.java()
     * @param id
     * @return String
     * @author wuhk
     * @date 2023/3/25 15:37
     **/
    @RequestMapping("/getById/{id}")
    //@SentinelResource(value = "getById",blockHandler = "getByIdBlockHandler")
    public String getById(@PathVariable("id") Integer id ){
        return "Sentienl热点异常处理！"+ name +"："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) ;
    }

    public String getByIdBlockHandler(@PathVariable("id") Integer id ,BlockException e){
        e.printStackTrace();
        return "Sentienl热点异常方法被流控了！！" + e;
    }

}
