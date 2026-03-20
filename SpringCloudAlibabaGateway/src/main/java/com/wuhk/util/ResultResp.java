package com.wuhk.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @className: ResultResp
 * @description: TODO
 * @author: wuhk
 * @date: 2023/12/27 16:24
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@AllArgsConstructor
public class ResultResp {
    private Integer code;
    private String messag;
}
