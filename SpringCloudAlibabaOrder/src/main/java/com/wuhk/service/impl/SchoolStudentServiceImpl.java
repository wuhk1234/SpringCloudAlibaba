package com.wuhk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuhk.entity.ComType;
import com.wuhk.entity.DevType;
import com.wuhk.entity.SchoolStudent;
import com.wuhk.mapper.SchoolStudentMapper;
import com.wuhk.service.SchoolStudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @className: SchoolStudentServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2023/7/21 10:18
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class SchoolStudentServiceImpl implements SchoolStudentService {
    @Autowired
    SchoolStudentMapper schoolStudentMapper;
    @Override
    public Page<SchoolStudent> gateSchoolStudentPageOne(int page, int pageSize) {
        //分页参数
        Page<SchoolStudent> rowPage = new Page(page, pageSize);
        //queryWrapper组装查询where条件
        LambdaQueryWrapper<SchoolStudent> queryWrapper = new LambdaQueryWrapper<>();
        rowPage = schoolStudentMapper.selectPage(rowPage, queryWrapper);
        return rowPage;
    }

    @Override
    public IPage<SchoolStudent> getPageStudentTwo(int page, int pageSize) {
        Page<SchoolStudent> rowPage = new Page(page, pageSize);
        SchoolStudent schoolStudent = new SchoolStudent();
        rowPage = schoolStudentMapper.getPageStudentTwo(rowPage,schoolStudent);
        return rowPage;
    }

    @Override
    public IPage<SchoolStudent> getPageStudentThree(Integer current, Integer size) {
        SchoolStudent schoolStudent = new SchoolStudent();
        Page pageStudentTwo = schoolStudentMapper.getPageStudentTwo(new Page(current, size), schoolStudent);
        return pageStudentTwo;
    }
    @Override
    public PageInfo<SchoolStudent> getPageStudentFour(Integer current, Integer size){
        PageHelper.startPage(current,size);
        List<SchoolStudent> list = schoolStudentMapper.getPageStudentFour();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public List<DevType> newTreeList(DevType devType) {
        List<DevType> list = schoolStudentMapper.getTree(devType);
        List<DevType> collect = list.stream().filter(item ->item.getParentId() == 0)
                .map(item -> {
                    item.setChild(getTree(item, list));
                    return item;
                }).collect(Collectors.toList());
        return collect.size() == 0 ? list : collect;
    }
    private List<DevType> getTree(DevType devType,List<DevType> devTypeList) {
        List<DevType> list = devTypeList.stream().filter(item -> item.getParentId().equals(devType.getId()))
                .map(item -> {
                    item.setChild(getTree(item,devTypeList));
                    return item;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<ComType> selectComTypeTrees(ComType comType) {
        List<ComType> comTypeList = schoolStudentMapper.selectComTypeTrees(comType);
        List<ComType> collect = comTypeList.stream()
                .filter(item -> item.getParentId() == 0)
                .map(item -> {
                    item.setChildList(getChildren(item, comTypeList));
                    return item;
                })
                .collect(Collectors.toList());
        return collect.size() == 0 ? comTypeList : collect;

    }

    public static List<ComType> getChildren(ComType comType, List<ComType> comTypeList) {
        List<ComType> collect = comTypeList.stream()
                .filter(item -> item.getParentId().equals(comType.getTypeId()))
                .map(item -> {
                    item.setChildList(getChildren(item, comTypeList));
                    return item;
                })
                .collect(Collectors.toList());
        return collect;
    }
}
