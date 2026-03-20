package com.wuhk.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @className: MyKafkaChannel
 * @description: 自定义发送通道
 * @author: wuhk
 * @date: 2023/6/13 15:11
 * @version: 1.0
 * @company 航天信息
 **/

public interface MyKafkaChannel {
    /**
     * 缺省发送消息通道名称
     */
    String MY_DEFAULT_OUTPUT = "my_default_output";

    /**
     * 缺省接收消息通道名称
     */
    String MY_DEFAULT_INPUT = "my_default_input";

    /**
     * 告警发送消息通道名称
     */
    String MY_ALARM_OUTPUT = "my_alarm_output";

    /**
     * 告警接收消息通道名称
     */
    String MY_ALARM_INPUT = "my_alarm_input";

    /**
     * 缺省发送消息通道
     * @return channel 返回缺省信息发送通道
     */
    @Output(MY_DEFAULT_OUTPUT)
    MessageChannel sendMyDefaultMessage();

    /**
     * 告警发送消息通道
     * @return channel 返回告警信息发送通道
     */
    @Output(MY_ALARM_OUTPUT)
    MessageChannel sendMyAlarmMessage();

    /**
     * 缺省接收消息通道
     * @return channel 返回缺省信息接收通道
     */
    @Input(MY_DEFAULT_INPUT)
    SubscribableChannel recieveMyDefaultMessage();

    /**
     * 告警接收消息通道
     * @return channel 返回告警信息接收通道
     */
    @Input(MY_ALARM_INPUT)
    SubscribableChannel recieveMyAlarmMessage();
}
