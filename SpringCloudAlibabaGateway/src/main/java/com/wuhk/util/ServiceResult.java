package com.wuhk.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @className: ResultResp
 * @description: 服务端通用数据序列化传递对象
 * @author: wuhk
 * @date: 2023/12/27 16:20
 * @version: 1.0
 * @company 航天信息
 **/
@Data
@Accessors(chain = true)
public class ServiceResult<T> implements Serializable {
    private static final long serialVersionUID = 6862740731843534519L;

    /**
     * 空字符串
     */
    private final static String NULL_STR = "";
    /**
     * 0字符串
     */
    private final static String ZERO_STR = "0";
    public static final Integer SUCCESS_CODE = 1000;
    public static final String SUCCESS_MSG = "成功";

    /**
     * 返回代码：1000-成功，其他-失败
     */
    private Integer code;

    /**
     * 错误描述：当code不为 1000 时，说明错误信息
     */
    private String message;

    /**
     * 返回数据对象
     */
    private T data;

    public ServiceResult() {
        this(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public ServiceResult(T data) {
        this(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public ServiceResult(Integer code, String message) {
        this(code, message, null);
    }

    public ServiceResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ServiceResult<T> ok() {
        return new ServiceResult<>();
    }

    public static <T> ServiceResult<T> ok(T data) {
        return new ServiceResult<>(data);
    }

    public static <T> ServiceResult<T> err(Integer code, String message) {
        return new ServiceResult<>(code, message);
    }

    public static ResultResp gatewayFailed(Integer code, String message) {
        ResultResp resultResp = new ResultResp(code, message);
        return resultResp;
    }
}
