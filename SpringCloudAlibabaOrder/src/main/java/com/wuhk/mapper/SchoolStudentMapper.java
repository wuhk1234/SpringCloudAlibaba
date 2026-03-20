package com.wuhk.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuhk.entity.ComType;
import com.wuhk.entity.DevType;
import com.wuhk.entity.SchoolStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: UserMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/21 10:05
 * @version: 1.0
 * @company 航天信息
 **/

public interface SchoolStudentMapper {
    Page<SchoolStudent>  selectPage(Page<SchoolStudent> rowPage, LambdaQueryWrapper<SchoolStudent> queryWrapper);

    Page<SchoolStudent> getPageStudentTwo(Page<SchoolStudent> rowPage,@Param("schoolStudent") SchoolStudent schoolStudent);

    List<SchoolStudent> getPageStudentFour();

    List<DevType> getTree(DevType devType);
    List<ComType> selectComTypeTrees(ComType comType);

}
