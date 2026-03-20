package com.wuhk.config;

import com.alibaba.fastjson.JSON;
import com.wuhk.entity.DeepSeekBodyEntity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @className: DeepSeekConfig
 * @description: TODO
 * @author: wuhk
 * @date: 2025/10/3 0003 16:42
 * @version: 1.0
 * @company 航天信息
 **/

@Component
public class DeepSeekConfig {
    @Value("${deepseek.api.key:sk-341e70f3bfec4cbd9af8ae0974d1a2ee}")
    private String apiKey;
    @Value("${deepseek.api.url:https://api.deepseek.com/v3.1_terminus_expires_on_20251015/chat}")
    private String apiUrl;
    public String callDeepSeek(String content) throws IOException {
        // OpenAI 兼容的 completions 接口
        String endpoint = apiUrl + "/completions";
        // 构建请求体
        String requestBody = String.format("{\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"content\": \""+content+"\",\n" +
                "            \"role\": \"system\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"model\": \"deepseek-chat\"\n" +
                "}");
        // 创建 HTTP 请求
        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer " + apiKey);
        httpPost.setEntity(new StringEntity(content));
        // 发送请求并获取响应
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
    }
}
