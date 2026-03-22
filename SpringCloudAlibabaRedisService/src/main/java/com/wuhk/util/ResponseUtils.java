package com.wuhk.util;

import com.wuhk.common.ResponseData;
import com.wuhk.common.ServiceResponse;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @className: ResponseUtils
 * @description: 统一处理响应数据
 * @author: wuhk
 * @date: 2026/3/21 0021 19:38
 * @version: 1.0
 * @company 航天信息
 **/

public class ResponseUtils {
    /**
     * 统一处理响应数据
     * @param serviceResponse
     * @return
     */
    public static ResponseData response(ServiceResponse serviceResponse) {
        if (serviceResponse.isSuccess()) {
            return ResponseUtils.success(serviceResponse);
        } else {
            return ResponseUtils.businessError(serviceResponse);
        }
    }

    public static ResponseData success(ServiceResponse serviceResponse) {
        ResponseData responseData = new ResponseData();
        if(null != serviceResponse.getMsg()){
            responseData.setMsg(serviceResponse.getMsg());
        }else{
            responseData.setMsg(ResponseCodeEnum.SUCCESS.getDescribe());
        }
        responseData.setCode(ResponseCodeEnum.SUCCESS.getCode());
        responseData.setSuccess(true);
        responseData.setData(serviceResponse.getData());
        return responseData;
    }

    public static ResponseData businessError(ServiceResponse serviceResponse) {
        ResponseData resultData = new ResponseData();
        resultData.setSuccess(false);
        if(serviceResponse.getCode() > 0){
            resultData.setCode(serviceResponse.getCode());
        }else{
            resultData.setCode(ResponseCodeEnum.SERVICE_BUSINESS_ERROR.getCode());
        }
        resultData.setMsg(serviceResponse.getMsg());
        return resultData;
    }

    public static ResponseData businessError(List<FieldError> errors) {
        ResponseData resultData = new ResponseData();
        resultData.setSuccess(false);
        resultData.setCode(ResponseCodeEnum.SERVICE_BUSINESS_ERROR.getCode());
        StringBuilder stringBuilder = new StringBuilder();
        if (errors != null) {
            for (FieldError error : errors) {
                stringBuilder.append(error.getField());
                stringBuilder.append(":");
                stringBuilder.append(error.getDefaultMessage());
                stringBuilder.append(" ");
            }
            resultData.setMsg(stringBuilder.toString());
        } else {
            resultData.setMsg(ResponseCodeEnum.UNKNOWN_BUSINESS_ERROR.getDescribe());
        }
        return resultData;
    }
}
