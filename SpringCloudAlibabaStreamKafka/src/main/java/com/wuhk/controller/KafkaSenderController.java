package com.wuhk.controller;

import com.wuhk.kafka.KafkaMessageSender;
import com.wuhk.service.KafkaConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: KafkaSenderController
 * @description: TODO
 * @author: wuhk
 * @date: 2023/6/13 15:06
 * @version: 1.0
 * @company 航天信息
 **/
@Slf4j
@RestController
@RequestMapping("/send")
public class KafkaSenderController {
    @Autowired
    private KafkaMessageSender kafkaMessageSender;

    @RequestMapping("/msg")
    public void send(String message){
        log.info("message:{}",message);
        kafkaMessageSender.sendToDefaultChannel(message);
        System.out.println("message = " + message);
    }
}
