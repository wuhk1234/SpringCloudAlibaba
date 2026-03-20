package com.wuhk.service;

import com.wuhk.entity.SysAdminMenu;

import java.util.List;
import java.util.Map;

/**
 * @className: SysAdminMenuService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/14 0014 14:00
 * @version: 1.0
 * @company 航天信息
 **/

public interface SysAdminMenuService {
    List<SysAdminMenu> getTreeMenuByUserId(Integer userId);
    List<SysAdminMenu> getMenusByUserId(Integer userId, Byte status);
    List<SysAdminMenu> buildByRecursiveTree(List<SysAdminMenu> rootSysAdminMenus);
    SysAdminMenu getChild(SysAdminMenu treeMenu, List<SysAdminMenu> treeNodes, int level);
    List<Map<String, Object>> getDataList(Integer userId, Byte status);
}
