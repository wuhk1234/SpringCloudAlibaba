package com.wuhk.service;

import com.wuhk.entity.SysAdminPost;

import java.util.List;

/**
 * @className: SysAdminPostService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/14 0014 14:00
 * @version: 1.0
 * @company 航天信息
 **/

public interface SysAdminPostService {
    List<SysAdminPost> getDataList(String name);
}
