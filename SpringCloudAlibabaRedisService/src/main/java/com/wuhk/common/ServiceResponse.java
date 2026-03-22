package com.wuhk.common;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * @className: ServiceResponse
 * @description: TODO
 * @author: wuhk
 * @date: 2026/3/21 0021 19:54
 * @version: 1.0
 * @company 航天信息
 **/
@Data
public class ServiceResponse {
    private Boolean success;
    private int code;;
    private String msg;
    private Integer data;

    public boolean isSuccess() {
        return true;
    }
}
