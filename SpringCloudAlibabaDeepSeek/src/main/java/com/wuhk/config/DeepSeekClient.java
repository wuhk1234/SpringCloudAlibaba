package com.wuhk.config;

import okhttp3.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @className: DeepSeekClient
 * @description: TODO
 * @author: wuhk
 * @date: 2025/11/4 0004 17:00
 * @version: 1.0
 * @company 航天信息
 **/

public class DeepSeekClient {
    private static final String API_URL = "https://api.deepseek.com/v1/chat/completions";
    private static final String API_KEY = "sk-341e70f3bfec4cbd9af8ae0974d1a2ee";

    public String sendRequest(String prompt) throws IOException {
        // 构建请求体
        String requestBody = "{\n" +
                "            \"model\": \"deepseek-chat\",\n" +
                "            \"messages\": [\n" +
                "                {\"role\": \"system\", \"content\": \""+ prompt + "\"},\n" +
                "            ],\n" +
                "            \"stream\": false\n" +
                "        }";

        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置请求头
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        // 发送请求体
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 读取响应
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            throw new IOException("HTTP error code: " + status);
        }
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    public String sendRequestTable(String prompt) throws IOException {
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
    public String callAI(String prompt) throws IOException {
        HttpPost request = new HttpPost(API_URL);
        //request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer " + API_KEY);

        StringEntity params = new StringEntity(
                "{\"model\":\"deepseek-chat\",\"messages\":[{\"role\":\"user\",\"content\":\""
                        + prompt + "\"}]}"
        );
        request.setEntity(params);

        return EntityUtils.toString(HttpClientBuilder.create().build().execute(request).getEntity());
    }
}
