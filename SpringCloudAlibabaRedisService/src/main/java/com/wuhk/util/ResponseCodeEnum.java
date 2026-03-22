package com.wuhk.util;

/**
 * @className: ResponseCodeEnum
 * @description: 接口响应结果CODE
 * @author: wuhk
 * @date: 2026/3/21 0021 19:39
 * @version: 1.0
 * @company 航天信息
 **/

public enum ResponseCodeEnum {
    /**
     * 返回成功
     */
    SUCCESS(1000, "OK"),
    /**
     * 全局异常
     */
    GLOBAL_EXCEPTION(4000, "Global Exception"),
    /**
     * 用户不存在
     */
    USER_NOTEXIST_EXCEPTION(4001, "用户不存在"),
    /**
     * 红包已抢完
     */
    RED_PACKET_FINISH_EXCEPTION(4002, "红包已抢完"),
    /**
     * 红包不存在
     */
    RED_PACKET_NOTEXIST_EXCEPTION(4003, "红包不存在"),
    /**
     * 不可重复拆红包
     */
    RED_PACKET_REPEAT_UNPACK_EXCEPTION(4004, "不可重复拆红包"),

    /**
     * Service业务错误
     */
    SERVICE_BUSINESS_ERROR(5000,"Service Business Error"),
    /**
     * 未知错误
     */
    UNKNOWN_BUSINESS_ERROR(5001,"Unknown Business Error");

    private int code;
    private String describe;

    ResponseCodeEnum(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
