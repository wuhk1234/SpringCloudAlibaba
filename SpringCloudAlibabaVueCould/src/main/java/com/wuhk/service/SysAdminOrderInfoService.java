package com.wuhk.service;

import com.github.pagehelper.PageInfo;
import com.wuhk.entity.SysAdminOrderInfo;

/**
 * @className: SysAdminOrderInfoService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/14 0014 14:00
 * @version: 1.0
 * @company 航天信息
 **/

public interface SysAdminOrderInfoService {
    PageInfo<SysAdminOrderInfo> getDataList(SysAdminOrderInfo record);
}
