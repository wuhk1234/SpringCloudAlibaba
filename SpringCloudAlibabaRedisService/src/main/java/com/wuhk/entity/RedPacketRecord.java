package com.wuhk.entity;

import lombok.Data;

import java.util.Date;

/**
 * @className: RedPacketRecord
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 20:10
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class RedPacketRecord {
    private String id;
    private Integer amount;
    private String nickName;
    private String imgUrl;
    private String uid;
    private String redPacketId;
    private Date createTime;
    private Date updateTime;

}
