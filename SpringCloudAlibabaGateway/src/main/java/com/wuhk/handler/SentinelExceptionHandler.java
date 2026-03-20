package com.wuhk.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.wuhk.util.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @className: SentinelExceptionHandler
 * @description: sentinel全局异常捕获
 * @author: wuhk
 * @date: 2023/12/25 16:55
 * @version: 1.0
 * @company 航天信息
 **/
public class SentinelExceptionHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        int code = 0;
        String msg = "";
        if (ex instanceof FlowException) {
            code = -1;
            msg = "限流了";
        } else if (ex instanceof DegradeException) {
            code = -2;
            msg = "降级了";
        } else if (ex instanceof ParamFlowException) {
            code = -3;
            msg = "参数限流了";
        } else if (ex instanceof SystemBlockException) {
            code = -4;
            msg = "系统负载异常了";
        } else if (ex instanceof AuthorityException) {
            code = -5;
            msg = "授权异常";
        } else {
            code = 0;
            msg = "未处理异常";
        }
        // JSON result by default.
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.
                        fromObject(ServiceResult.gatewayFailed(code, msg)));
    }
}

