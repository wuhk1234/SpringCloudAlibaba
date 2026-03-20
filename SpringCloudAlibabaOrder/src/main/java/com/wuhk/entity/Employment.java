package com.wuhk.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @className: Employment
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/16 0016 19:21
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@TableName("employment")
public class Employment {
    @TableId(type = IdType.AUTO)
    @ExcelProperty(value="id")
    private Long id;
    @ExcelProperty(value="员工编号")
    private int employmentNo;//员工编号
    @ExcelProperty(value="姓名")
    private String name;//姓名
    @ExcelProperty(value="邮箱")
    private String email;//邮箱
    @ExcelProperty(value="部门")
    private String department;//部门
    @ExcelProperty(value="基本工资")
    private BigDecimal baseSalary;//基本工资
    @ExcelProperty(value="奖金")
    private BigDecimal bonus;//奖金
    @ExcelProperty(value="入职时间")
    private String hireDate;//入职时间
    @ExcelProperty(value="状态")
    private String status;//状态
}
