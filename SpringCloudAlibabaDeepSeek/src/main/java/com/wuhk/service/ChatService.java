/*
package com.wuhk.service;

import com.wuhk.entity.ChatMessage;
import org.springframework.ai.chat.messages.Message;
import reactor.core.publisher.Mono;

import java.util.List;

*/
/**
 * @className: ChatService
 * @description: TODO
 * @author: wuhk
 * @date: 2025/10/29 0029 0:51
 * @version: 1.0
 * @company 航天信息
 **//*


public interface ChatService {
    String initOrUpdateSession(String userId, Integer expireSeconds);
    void saveMessages(List<ChatMessage> messages);
    List<Message> getRecentMessages(String chatId, int maxCount);
    Mono<Void> saveAssistantMessage(String chatId, String fullContent);
}
*/
