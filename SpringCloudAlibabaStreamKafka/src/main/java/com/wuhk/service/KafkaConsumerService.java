package com.wuhk.service;

import com.wuhk.kafka.MyKafkaChannel;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @className: KafkaConsumerService
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/13 15:58
 * @version: 1.0
 * @company 航天信息
 **/

public interface KafkaConsumerService {
    void getMessage(String message);
}
