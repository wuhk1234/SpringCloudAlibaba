package com.wuhk.common;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @className: ResponseData
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 20:00
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class ResponseData {
    private Boolean success;
    private int code;
    private String msg;
    private Integer data;

    public boolean isSuccess() {
        return true;
    }
}
