package com.woniuxy.service.impl;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "first",consumerGroup = "mygroup")
public class Consumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("消费者开始消费消息："+message);
    }
}
