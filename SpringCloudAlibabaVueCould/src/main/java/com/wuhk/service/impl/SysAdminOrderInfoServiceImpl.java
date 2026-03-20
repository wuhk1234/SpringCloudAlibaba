package com.wuhk.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuhk.base.BaseServiceImpl;
import com.wuhk.mapper.SysAdminOrderInfoMapper;
import com.wuhk.entity.SysAdminOrderInfo;
import com.wuhk.service.SysAdminOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @className: OrderService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/13 0013 20:07
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class SysAdminOrderInfoServiceImpl extends BaseServiceImpl<SysAdminOrderInfo> implements SysAdminOrderInfoService {
    @Autowired
    private SysAdminOrderInfoMapper sysAdminOrderInfoMapper;
    @Override
    public Mapper<SysAdminOrderInfo> getMapper() {
        return sysAdminOrderInfoMapper;
    }

    @Override
    public PageInfo<SysAdminOrderInfo> getDataList(SysAdminOrderInfo record) {
        return super.selectPage(record.getPage(), record.getRows(), record);
    }

}
