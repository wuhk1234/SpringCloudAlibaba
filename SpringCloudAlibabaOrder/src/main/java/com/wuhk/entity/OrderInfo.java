package com.wuhk.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: OrderInfo
 * @description: TODO
 * @author: wuhk
 * @date: 2023/8/8 10:53
 * @version: 1.0
 * @company 航天信息
 **/

@Data
public class OrderInfo {
    private int id;
    private int period;//账期月份
    private BigDecimal amount;//金额
    private String userName;//下单人
    private String phone;//手机号
    private String created;//创建时间
    private String creator;//创建人
    private String modified;//修改时间
    private String modifier;//修改人
    private int pageNum;//页数
    private int pageSize;//每页所返回行数
}
