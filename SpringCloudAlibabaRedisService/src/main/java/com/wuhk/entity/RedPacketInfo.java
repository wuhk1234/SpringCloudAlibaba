package com.wuhk.entity;

import lombok.Data;

import java.util.Date;

/**
 * @className: RedPacketInfo
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 20:05
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class RedPacketInfo {
    private String id;
    private String redPacketId;
    private Integer totalAmount;
    private Integer totalPacket;
    private Integer remainingAmount;
    private Integer remainingPacket;
    private String uid;
    private Date createTime;
    private Date updateTime;
}
