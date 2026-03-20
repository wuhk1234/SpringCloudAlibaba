package com.wuhk.entity;

import okhttp3.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: TestDeepSeek
 * @description: TODO
 * @author: wuhk
 * @date: 2025/11/4 0004 19:18
 * @version: 1.0
 * @company 航天信息
 **/

public class TestDeepSeek {
    private static final String API_URL = "https://api.deepseek.com/chat/completions";
    private static final String API_KEY = "sk-341e70f3bfec4cbd9af8ae0974d1a2ee";

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    public String sendRequest(String prompt) throws IOException {
        String requestBody = "{\n" +
                "            \"model\": \"deepseek-chat\",\n" +
                "            \"messages\": [\n" +
                "                {\"role\": \"system\", \"content\": \""+ prompt + "\"},\n" +
                "            ],\n" +
                "            \"stream\": false\n" +
                "        }";

        RequestBody body = RequestBody.create(requestBody, JSON);

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    public static String sendRequest() {
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        //headers.setContentType(mediaType.);
        headers.set("Authorization", "Bearer " + API_KEY);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("content", "You are a helpful assistant");
        messages.add(map);

        requestBody.put("messages", messages);
        requestBody.put("stream", false);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    public static void main(String[] args) {
        try {
            String response = sendRequest();
            System.out.println("Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
