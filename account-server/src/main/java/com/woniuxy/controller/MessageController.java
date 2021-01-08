package com.woniuxy.controller;

import com.woniuxy.domain.Order;
import com.woniuxy.service.impl.OrderService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class MessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private OrderService orderService;

    @GetMapping("send")
    public SendResult send(){
        Message<String> message = MessageBuilder.withPayload("hello rocketMQ").build();
        SendResult first = rocketMQTemplate.syncSend("first", message);
        return first;
    }
    //同步消息
    @GetMapping("send2")
    public String send2(){
        Message<String> message = MessageBuilder.withPayload("加上tag与group的消息")
                .setHeader(MessageConst.PROPERTY_KEYS,"key1")
                .build();
        rocketMQTemplate.syncSend("first:group1",message);
        return "同步消息";
    }

    //异步消息
    @GetMapping("send3")
    public String send3(){
        Message<String> message = MessageBuilder.withPayload("异步消息内容")
                .setHeader(MessageConst.PROPERTY_KEYS, UUID.randomUUID().toString())
                .build();
        rocketMQTemplate.asyncSend("first:group1", message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                //发送成功
                System.out.println("消息发送成功"+message);
            }

            @Override
            public void onException(Throwable throwable) {
                //发送失败
                System.out.println("消息发送失败"+throwable.getMessage());
            }
        });
        return "异步消息测试";
    }

    //延时消息
    @GetMapping("send4")
    public String send4(){
        Message<String> message = MessageBuilder.withPayload("延时消息测试")
                .setHeader(MessageConst.PROPERTY_KEYS, UUID.randomUUID().toString())
                .build();
        rocketMQTemplate.syncSend("first:group2",message,5000,2);
        return "延时消息测试";
    }

    //顺序消息
    @GetMapping("send5")
    public String send5(){
        Message<String> message = MessageBuilder.withPayload("顺序消息测试")
                .setHeader(MessageConst.PROPERTY_KEYS, UUID.randomUUID().toString())
                .build();
        rocketMQTemplate.asyncSendOrderly("first:group2", message, "hashkey", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送顺序消息成功："+message);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送顺序消息失败："+throwable.getMessage());
            }
        });

        return "顺序消息测试";
    }

    //单项消息
    @GetMapping("send6")
    public String send6(){
        Message<String> message = MessageBuilder.withPayload("单项消息测试")
                .setHeader(MessageConst.PROPERTY_KEYS, UUID.randomUUID().toString())
                .build();
        rocketMQTemplate.sendOneWay("first:group1",message);
        return "单项消息测试";
    }

    //事务消息
    @GetMapping("send7")
    public String send7(){
        Message<String> message = MessageBuilder.withPayload("事务消息测试")
                .setHeader(MessageConst.PROPERTY_KEYS, UUID.randomUUID().toString())
                .setHeader("status",UUID.randomUUID().toString())
                .build();
        Order order = new Order();
        order.setId(1);
        order.setName("测试专用订单");
        rocketMQTemplate.sendMessageInTransaction("first:group2",message,order);
        System.out.println("发送半消息成功");
        return "事务消息测试";
    }

    @GetMapping("add")
    public String add(){
        orderService.add();
        return "";
    }

    @GetMapping("addI")
    public String addI(){
        orderService.addI();
        return "";
    }
}
