package com.wuhk.conltroller;

//import org.springframework.ai.chat.client.ChatClient;
import com.wuhk.config.DeepSeekClient;
import com.wuhk.config.DeepSeekConfig;

import com.wuhk.entity.MessagesEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @className: TestAiController
 * @description: TODO
 * @author: wuhk
 * @date: 2025/10/3 0003 17:06
 * @version: 1.0
 * @company 航天信息
 **/
@RestController
@CrossOrigin
@RequestMapping("/api")
public class TestAiController {
    //private final ChatClient chatClient;
    //private final ChatService chatService; // 注入 ChatService
    private static final String DEFAULT_PROMPT = "你是一个聊天助手，请根据用户问题，进行简短的回答！";

    @Autowired
    private DeepSeekConfig deepSeekConfig;
    @PostMapping("/query")
    public ResponseEntity<String> handleQuery(@RequestBody MessagesEntity messagesEntity) throws IOException {
        DeepSeekClient deepSeekClient = new DeepSeekClient();

        String response = deepSeekClient.sendRequest(messagesEntity.getContent());
        return ResponseEntity.ok(response);

    }
    //没法自动导入，所以要构造一下
    /*private ChatClient chatClient;
    public TestAiController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }*/

    @RequestMapping("/ask")
    public String askDeepSeek(@RequestParam(value = "content")String content) {
        try {
            return deepSeekConfig.callDeepSeek(content);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error calling DeepSeek API";
        }
    }
    @CrossOrigin
    @GetMapping(value = "/test")
    public String test(@RequestParam(value = "message")String message) {
        //首先通过prompt工程，然后在设置问题，最后通过call方法调用
        //sk-341e70f3bfec4cbd9af8ae0974d1a2ee
        return  "111111";//chatClient.prompt().user(message).call().content();
    }

    private RestTemplate restTemplate;
    private static final String AI_SERVICE_URL = "https://api.deepseek.com/v3.1_terminus_expires_on_20251015/chat/completions"; // 根据实际API地址修改
    private static final String API_KEY = "sk-341e70f3bfec4cbd9af8ae0974d1a2ee"; // 你的API密钥


    @GetMapping("/ai/predict")
    public ResponseEntity<String> getPrediction() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY); // 设置API密钥或令牌
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(AI_SERVICE_URL + "predict", HttpMethod.GET, entity, String.class);
        return response;
    }

    /*public TestAiController(ChatClient.Builder chatClientBuilder, ChatService chatService) {
        this.chatService = chatService;
        this.chatClient = chatClientBuilder
                .defaultSystem(DEFAULT_PROMPT)  // 设置默认系统提示
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()) // 上下文记忆
                )
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()  // 日志记录
                )
                .defaultOptions(  // 模型参数
                        OpenAiChatOptions.builder().topP(0.7).build()
                )
                .build();
    }

    @GetMapping("/simple0")
    public String simpleChat(@RequestParam String message) {
        return chatClient.prompt(message).call().content();
    }

    @GetMapping("/simple1")
    public String simpleChat(@RequestParam String message, @RequestParam String chatId) {
        return chatClient.prompt(message)
                .advisors(a -> a
                        .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                ).call().content();
    }

    @GetMapping("/stream")
    public Flux<String> streamChat(@RequestParam String message, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        return chatClient.prompt(message).stream().content();
    }

    @GetMapping(value = "/stream/response", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamChat(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .map(content -> ServerSentEvent.<String>builder().data(content).build());
    }


    *//**
     * 流式响应并保存到数据库
     * @param message 用户消息
     * @param userId 用户ID（从请求参数或 Token 中获取）
     *//*
    @GetMapping(value = "/stream/sql", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamChat(
            @RequestParam String message,
            @RequestParam String userId
    ) {
        // 1. 初始化会话并保存用户消息
        String chatId = chatService.initOrUpdateSession(userId, 3600);
        *//*chatService.saveMessages(List.of(
                new ChatMessage(chatId, "user", message, LocalDateTime.now())
        ));
        // 2. 获取历史上下文
        List<Message> history = chatService.getRecentMessages(chatId, 10);
        // 3. 调用 OpenAI 获取流式响应
        Flux<String> contentFlux = chatClient.prompt()
                .messages(history)
                .user(message)
                .stream()
                .content();
        // 4. 共享流以便多次订阅
        Flux<String> sharedFlux = contentFlux.share();
        // 5. 异步保存完整回复到数据库（流结束后触发）
        sharedFlux.collectList()
                .flatMap(contentList -> {
                    String fullContent = String.join("", contentList);
                    return chatService.saveAssistantMessage(chatId, fullContent)
                            .subscribeOn(Schedulers.boundedElastic());
                })
                .subscribe();
        // 6. 实时返回流式响应
        return sharedFlux.map(content -> ServerSentEvent.builder(content).build());*//*
        return null;
    }*/
}