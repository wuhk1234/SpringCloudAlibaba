package com.wuhk.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @className: KafkaMessageSender
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/13 15:01
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@Component
//第一种形式：结合Stream包
//@EnableBinding(value = Source.class)
//第二种：自定义
@EnableBinding(MyKafkaChannel.class)
public class KafkaMessageSender {

    //第一种形式：结合Stream包
    /*@Autowired
    private Source channel;

    public void sendToDefaultChannel(String message) {
        channel.output().send(MessageBuilder.withPayload(message).build());
    }*/
    @Autowired
    private MyKafkaChannel channel;

    /**
     * 消息发送到默认通道：缺省通道对应缺省主题
     *
     * @param message
     */
    public void sendToDefaultChannel(String message) {
        channel.sendMyDefaultMessage().send(MessageBuilder.withPayload(message).build());
    }

    public void sendToAlarmChannel(String message) {
        channel.sendMyAlarmMessage().send(MessageBuilder.withPayload(message).build());
    }
}
