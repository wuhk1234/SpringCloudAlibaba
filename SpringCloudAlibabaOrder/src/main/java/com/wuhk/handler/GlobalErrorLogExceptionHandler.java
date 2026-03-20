package com.wuhk.handler;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.wuhk.bean.ServiceResult;
import com.wuhk.enums.GeneralStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @className: GlobalErrorLogExceptionHandler
 * @description: 日志服务全局异常捕获
 * @author: wuhk
 * @date: 2022/9/6 11:27
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@RestControllerAdvice
@Order(-2)
public class GlobalErrorLogExceptionHandler {

    /**
     * <p>非法参数验证异常</p>
     * @method GlobalErrorLogExceptionHandler.handleMethodArgumentNotValidExceptionHandler()
     * @param ex：异常方法错误
     * @return ServiceResult
     * @author wuhk
     * @date 2022/9/6 14:16
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ServiceResult handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String defaultMessage = result.getFieldError().getDefaultMessage();
        if (StringUtils.isBlank(defaultMessage)){
            return ServiceResult.err(Integer.valueOf(result.getFieldError().getCode()),defaultMessage);
        }
        return ServiceResult.err(Integer.valueOf(result.getFieldError().getCode()),defaultMessage);
    }

    /**
     * <p>HTTP解析请求参数异常</p>
     * @method GlobalErrorLogExceptionHandler.httpMessageNotReadableException()
     * @param e：请求参数异常
     * @return ServiceResult
     * @author wuhk
     * @date 2022/9/6 15:00
     **/
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServiceResult httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("httpMessageNotReadableException:[e:{}]", e.getMessage());
        return ServiceResult.err(GeneralStatusCode.PARAMETER_PARSE_EXCEPTION.getCode(), GeneralStatusCode.PARAMETER_PARSE_EXCEPTION.getMessage());
    }

    /**
     * <p>HTTP解析请求类型异常</p>
     * @method GlobalErrorLogExceptionHandler.httpMediaTypeException()
     * @param exception：类型异常
     * @return ServiceResult
     * @author wuhk
     * @date 2022/9/6 15:19
     **/
    @ExceptionHandler(value = HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServiceResult httpMediaTypeException(HttpMediaTypeException exception) {
        log.error("httpMediaTypeException:[exception:{}]", exception.getMessage());
        return ServiceResult.err(GeneralStatusCode.HTTP_MEDIA_TYPE_EXCEPTION.getCode(), GeneralStatusCode.HTTP_MEDIA_TYPE_EXCEPTION.getMessage());
    }

    /**
     * <p>SQL 语法异常</p>
     * @method GlobalErrorLogExceptionHandler.badSqlGrammarException()
     * @param exception：sql异常信息
     * @return
     * @author wuhk
     * @date 2022/9/6 15:20
     **/
    @ExceptionHandler(value = BadSqlGrammarException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServiceResult badSqlGrammarException(BadSqlGrammarException exception) {
        log.info("badSqlGrammarException:[exception:{}]", exception.getMessage());
        return ServiceResult.err(GeneralStatusCode.SQL_ERROR_EXCEPTION.getCode(),GeneralStatusCode.SQL_ERROR_EXCEPTION.getMessage());
    }

    /**
     * <p>默认的异常处理</p>
     * @method GlobalErrorLogExceptionHandler.exceptionHandler()
     * @param exception：默认的异常
     * @return ServiceResult
     * @author wuhk
     * @date 2022/9/6 15:23
     **/
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ServiceResult exceptionHandler(Exception exception) {
        log.error("exceptionHandler:[exception:{}]", exception.getMessage());
        if (Objects.nonNull(exception.getMessage())) {
            return ServiceResult.err(GeneralStatusCode.SYSTEM_EXCEPTION.getCode(),GeneralStatusCode.SYSTEM_EXCEPTION.getMessage());
        }
        return ServiceResult.err(GeneralStatusCode.SYSTEM_EXCEPTION.getCode(),GeneralStatusCode.SYSTEM_EXCEPTION.getMessage());
    }

    /**
     * <p>默认的异常处理</p>
     * @method GlobalErrorLogExceptionHandler.exceptionHandler()
     * @param exception：默认的异常
     * @return ServiceResult
     * @author wuhk
     * @date 2022/9/6 15:23
     **/
    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServiceResult arithmeticException(ArithmeticException exception) {
        log.error("exceptionHandler:[exception:{}]", exception.getMessage());
        if (Objects.nonNull(exception.getMessage())) {
            return ServiceResult.err(GeneralStatusCode.SYSTEM_EXCEPTION.getCode(),GeneralStatusCode.SYSTEM_EXCEPTION.getMessage());
        }
        return ServiceResult.err(GeneralStatusCode.SYSTEM_EXCEPTION.getCode(),GeneralStatusCode.SYSTEM_EXCEPTION.getMessage());
    }

    /**
     * <p>请求的方法或类型异常</p>
     * @method GlobalErrorLogExceptionHandler.servletRequestBindingException()
     * @param request：请求信息
     * @param e:异常信息
     * @return ServiceResult
     * @author wuhk
     * @date 2022/9/15 15:18
     **/
    @ExceptionHandler({ServletRequestBindingException.class, HttpRequestMethodNotSupportedException.class})
    public ServiceResult servletRequestBindingException(HttpServletRequest request, Exception e) {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        log.info("url.............:{}", request.getRequestURL());
        log.info("IP             : {}", request.getRemoteAddr());
        log.error(e.getMessage(), e);
        return ServiceResult.err(GeneralStatusCode.HTTP_MEDIA_TYPE_EXCEPTION.getCode(), GeneralStatusCode.HTTP_MEDIA_TYPE_EXCEPTION.getMessage());
    }
}
