package com.wuhk.service.impl;

import com.wuhk.entity.OrderSeata;
import com.wuhk.feign.RedisFeginService;
import com.wuhk.feign.StockFeignService;
import com.wuhk.feign.StockSeataFeignService;
import com.wuhk.mapper.OrderSeataMapper;
import com.wuhk.service.OrderSeataService;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Tags;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: OrderSeataServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/28 16:15
 * @version: 1.0
 * @company 航天信息
 **/
@Service
@Slf4j
public class OrderSeataServiceImpl implements OrderSeataService {
    @Resource
    private OrderSeataMapper OrderSeataMapper;

    @Autowired
    private StockSeataFeignService stockSeataFeignService;

    @Autowired
    private RedisFeginService redisFeginService;


    //@Transactional //本地事务会导致远程调用的接口返回成功
    //分布式事务
    @GlobalTransactional
    @Override
    @GlobalLock
    public void creat(OrderSeata orderSeata) {
        log.info("seate分布式事务");
        OrderSeataMapper.creat(orderSeata);
        stockSeataFeignService.update(orderSeata.getProductId());
        //redisFeginService.set(orderSeata.getName());
        int a = 1/0;
    }

    @Override
    @Trace
    @Tag(key = "list",value = "returnedObj")
    public List<OrderSeata> list() throws InterruptedException {
        Thread.sleep(2000);
        return OrderSeataMapper.list();
    }

    @Override
    @Trace
    @Tags({@Tag(key = "orderSeataById",value = "args[0]"),
            @Tag(key = "orderSeataById",value = "returnedObj")})
    public OrderSeata orderSeataById(Integer id) {
        return OrderSeataMapper.orderSeataById(id);
    }
}
