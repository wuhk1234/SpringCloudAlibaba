package com.wuhk.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: MyBolckExceptionHandler
 * @description: 全局流控异常处理
 * @author: wuhk
 * @date: 2023/3/14 20:06
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@Component
public class MyBolckExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        System.out.println("资源规则的详细信息：" + e.getRule());
        JSONObject resultObj = new JSONObject();
        if(e instanceof FlowException){
            resultObj.put("code",100);
            resultObj.put("msg","接口限流");
        }if(e instanceof DegradeException){
            resultObj.put("code",101);
            resultObj.put("msg","服务降级");
        }if(e instanceof ParamFlowException){
            resultObj.put("code",102);
            resultObj.put("msg","热点参数限流");
        }if(e instanceof SystemBlockException){
            resultObj.put("code",103);
            resultObj.put("msg","触发系统保护规则");
        }if(e instanceof AuthorityException){
            resultObj.put("code",104);
            resultObj.put("msg","授权规则不通过");
        }

        //返回json数据
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.getWriter().write(resultObj.toString());
    }
}
