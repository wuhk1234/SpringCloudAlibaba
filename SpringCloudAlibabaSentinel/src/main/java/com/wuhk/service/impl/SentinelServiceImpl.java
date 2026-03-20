package com.wuhk.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.wuhk.service.SentinelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @className: SentinelServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/15 20:20
 * @version: 1.0
 * @company 航天信息
 **/
@Service
@Slf4j
public class SentinelServiceImpl implements SentinelService {
    @Override
    @SentinelResource(value = "getUser",blockHandler = "getUserBlockHandler")
    public String getUser() {
        return "链路流控！！！！！！";
    }

    public String getUserBlockHandler(BlockException b){
        b.printStackTrace();
        return "用户被流控了！！！";
    }
}
