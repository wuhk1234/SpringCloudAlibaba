package com.wuhk.kafka;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

import java.util.Date;

/**
 * @className: KafkaMessageReceiveListener
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/13 14:56
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
//第一种形式：结合Stream包
//@EnableBinding(value = Sink.class)
@EnableBinding(value = MyKafkaChannel.class)
public class KafkaMessageReceiveListener {
    //第一种形式：结合Stream包
    /*@StreamListener(Sink.INPUT)
    public void recevie(Message<String> message) {
        log.info("{}订阅告警消息：通道 = es_default_input，data = {}", new Date(), message);
    }*/

    /**
     * 从缺省通道接收消息
     * @param message
     */
    @StreamListener(MyKafkaChannel.MY_DEFAULT_INPUT)
    public void receive(Message<String> message){
        log.info("{}订阅缺省消息：通道 = es_default_input，data = {}", new Date(), message);
    }

    @StreamListener(MyKafkaChannel.MY_ALARM_INPUT)
    public void receiveAlarm(Message<String> message){
        log.info("{}订阅告警消息：通道 = my_alarm_input，data = {}", new Date(), message);
    }
}
