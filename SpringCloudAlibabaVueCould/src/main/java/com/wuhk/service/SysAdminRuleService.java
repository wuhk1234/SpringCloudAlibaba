package com.wuhk.service;

import com.wuhk.entity.SysAdminRule;

import java.util.List;
import java.util.Map;

/**
 * @className: SysAdminRuleService
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/14 0014 14:00
 * @version: 1.0
 * @company 航天信息
 **/

public interface SysAdminRuleService {
    List<SysAdminRule> getTreeRuleByUserId(Integer userId);
    List<SysAdminRule> buildByRecursiveTree(List<SysAdminRule> rootSysAdminRules);
    SysAdminRule getChild(SysAdminRule treeMenu, List<SysAdminRule> treeNodes, int level);
    List<String> rulesDeal(List<SysAdminRule> treeNodes);
    List<Map<String, Object>> getDataList(Integer userId, String type);
    List<SysAdminRule> getRulesByUserId(Integer userId);
}
