package com.wuhk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @className: ImageVO
 * @description: 图片实体
 * @author: wuhk
 * @date: 2023/4/4 10:21
 * @version: 1.0
 * @company 航天信息
 **/
@AllArgsConstructor
@Data
public class ImageVO {
    private String capcode;
    private int xpos;
}
