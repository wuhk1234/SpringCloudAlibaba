package com.wuhk.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.wuhk.entity.ComType;
import com.wuhk.entity.DevType;
import com.wuhk.entity.SchoolStudent;

import java.util.List;

/**
 * @className: SchoolStudentService
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/21 10:17
 * @version: 1.0
 * @company 航天信息
 **/

public interface SchoolStudentService {
    Page<SchoolStudent> gateSchoolStudentPageOne(int page, int pageSize);
    IPage<SchoolStudent> getPageStudentTwo(int page, int pageSize);
    IPage<SchoolStudent> getPageStudentThree(Integer current, Integer size);
    PageInfo<SchoolStudent> getPageStudentFour(Integer current, Integer size);
    List<DevType> newTreeList(DevType devType);
    List<ComType> selectComTypeTrees(ComType comType);
}
