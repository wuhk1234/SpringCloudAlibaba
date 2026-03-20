package com.wuhk.service.impl;

import com.wuhk.kafka.MyKafkaChannel;
import com.wuhk.service.KafkaConsumerService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * @className: KafkaConsumerServiceImpl
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/13 16:02
 * @version: 1.0
 * @company 航天信息
 **/
@Service
public class KafkaConsumerServiceImpl {
    @StreamListener(MyKafkaChannel.MY_DEFAULT_INPUT)
    public void getMessage(String message) {
        System.out.println("messagewwwwwwwwwwwwwwwwwwwwwwwwwwwww: " + message);
    }
}
