package com.wuhk.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuhk.entity.Employment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: EmploymentMapper
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/16 0016 21:18
 * @version: 1.0
 * @company 航天信息
 **/
@Mapper
public interface EmploymentMapper extends BaseMapper<Employment> {
    List<Employment> batchExport(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    List<Employment> batchExportNotParam();

}
