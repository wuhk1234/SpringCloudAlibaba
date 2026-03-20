package com.wuhk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @className: Captcha
 * @description: 验证码实体
 * @author: wuhk
 * @date: 2023/4/3 20:58
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@AllArgsConstructor
public class VerificationCodePlace {
    private String backName;
    private String markName;
    private int xLocation;
    private int yLocation;

}
