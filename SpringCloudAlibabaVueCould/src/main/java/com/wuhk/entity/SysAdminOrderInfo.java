package com.wuhk.entity;

import com.wuhk.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @className: Order
 * @description: TODO
 * @author: wuhk
 * @date: 2023/3/28 16:13
 * @version: 1.0
 * @company 航天信息
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "sys_admin_order_info")
public class SysAdminOrderInfo extends BaseEntity {
    private static final long serialVersionUID = 7046525700737221455L;
    @Column(name = "`id`")
    private Integer id;
    @Column(name = "`period`")
    private String period;
    @Column(name = "`amount`")
    private String amount;
    @Column(name = "`user_name`")
    private String userName;
    @Column(name = "`phone`")
    private String phone;
    @Column(name = "`created`")
    private Timestamp created;
    @Column(name = "`creator`")
    private String creator;
    @Column(name = "`modified`")
    private Timestamp modified;
    @Column(name = "`modifier`")
    private String modifier;
    /**
     * 状态
     */
    @Column(name = "`status`")
    private Byte status;
}
