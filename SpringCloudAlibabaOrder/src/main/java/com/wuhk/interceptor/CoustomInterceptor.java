package com.wuhk.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: CoustomInterceptor
 * @description: fegin的自定义拦截器
 * @author: wuhk
 * @date: 2023/3/11 10:00
 * @version: 1.0
 * @company 航天信息
 **/

public class CoustomInterceptor implements RequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("name","wuhk");
        requestTemplate.query("id","1213");
        logger.info("fegin的自定义拦截器！");
    }
}
