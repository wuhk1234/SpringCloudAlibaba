package com.wuhk.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: OrderInfoExcel
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/8 10:53
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class OrderInfoExcel {
    @ExcelProperty(value="id")
    private int id;
    @ExcelProperty(value="账期月份")
    private int period;//账期月份
    @ExcelProperty(value="金额")
    private BigDecimal amount;//金额
    @ExcelProperty(value="下单人")
    private String userName;//下单人
    @ExcelProperty(value="手机号")
    private String phone;//手机号
    @ExcelProperty(value="创建时间")
    private String created;//创建时间
    @ExcelProperty(value="创建人")
    private String creator;//创建人
    @ExcelProperty(value="修改时间")
    private String modified;//修改时间
    @ExcelProperty(value="修改人")
    private String modifier;//修改人
    @ExcelIgnore
    private int pageNum;//页数
    @ExcelIgnore
    private int pageSize;//每页所返回行数
}
