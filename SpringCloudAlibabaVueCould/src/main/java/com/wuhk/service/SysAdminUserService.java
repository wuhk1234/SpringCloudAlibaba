package com.wuhk.service;

import com.github.pagehelper.PageInfo;
import com.wuhk.entity.SysAdminUser;

/**
 * @className: SysAdminUserService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/14 0014 14:01
 * @version: 1.0
 * @company 航天信息
 **/

public interface SysAdminUserService {
    String setInfo(SysAdminUser currentUser, String old_pwd, String new_pwd);
    PageInfo<SysAdminUser> getDataList(SysAdminUser record);
}
