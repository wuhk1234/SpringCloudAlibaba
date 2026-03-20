package com.wuhk.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @className: AclDownloadGetGatewayFilterFactory
 * @description: 文件下载鉴权判断过滤器
 * @author: wuhk
 * @date: 2023/3/20 9:02
 * @version: 1.0
 * @company 航天信息
 **/
@Component
@Slf4j
public class AclDownloadGetGatewayFilterFactory extends AbstractGatewayFilterFactory<AclDownloadGetGatewayFilterFactory.Config> {

    //构造函数，加载Config
    public AclDownloadGetGatewayFilterFactory() {
        //固定写法,这里将自定义的config传过去，否则会报告ClassCastException
        super(AclDownloadGetGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(AclDownloadGetGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            String reqPath = exchange.getRequest().getURI().getPath();
            log.info("请求路径：{}", reqPath);
            //直接调用鉴权
            // 1. header 中必须有 Authorization
            String encodedClientToken = exchange.getRequest().getHeaders().getFirst("");
            //税务人工作流要删除用户令牌
            return chain.filter(exchange);
        };
    }

    /**
     * 自定义的config类，用来设置传入的参数
     */
    public static class Config {
        //Put the configuration properties for your filter here
    }

    /**
     * <p>路径截取</p>
     * @method AclDownloadGetGatewayFilterFactory.extractString()
     * @param s:路径参数
     * @return String
     * @author wuhk
     * @date 2023/3/20 9:18
     **/
    private static String extractString(String s) {
        for (int i = 0; i < 3; i++) {
            s = s.substring(s.indexOf("/") + 1);
        }
        return "/" + s;
    }
}
