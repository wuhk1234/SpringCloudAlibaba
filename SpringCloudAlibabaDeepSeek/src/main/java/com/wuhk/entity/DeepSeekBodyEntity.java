package com.wuhk.entity;

import lombok.Data;

import java.util.List;

/**
 * @className: DeepSeekBodyEntity
 * @description: TODO
 * @author: wuhk
 * @date: 2025/10/3 0003 20:49
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class DeepSeekBodyEntity {
    private String model = "deepseek-chat";
    private List<MessagesEntity> messages;
    private String stream = "false";
}
