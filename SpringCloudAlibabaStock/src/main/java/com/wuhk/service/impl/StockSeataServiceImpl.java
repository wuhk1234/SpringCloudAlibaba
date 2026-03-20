package com.wuhk.service.impl;

import com.wuhk.entity.StockSeata;
import com.wuhk.mapper.StockSeataMapper;
import com.wuhk.service.StockSeataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
public class StockSeataServiceImpl implements StockSeataService {
    @Resource
    private StockSeataMapper stockSeataMapper;

    @Override
    public void creat(StockSeata stockSeata) {
        stockSeataMapper.creat(stockSeata);
    }

    @Override
    public void update(Integer stockSeata) {
        log.info("stockSeata更新库存:" + stockSeata);
        stockSeataMapper.updata(stockSeata);
    }
}
