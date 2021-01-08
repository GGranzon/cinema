package com.woniuxy.controller;

import com.woniuxy.dto.OrderDto;
import com.woniuxy.dto.Result;

import com.woniuxy.parameter.OrderParameter;
import com.woniuxy.service.OrderService;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class OrderController {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private OrderService orderService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    //购票下单
    @GetMapping("order")
    public Result order(OrderParameter orderParameter){
        if(!ObjectUtils.isEmpty(orderParameter)){
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderParameter,orderDto);
            Result result = orderService.order(orderDto);

            //生成消息，让消费者更改商品状态为 “待付款”
            Message<OrderParameter> message = MessageBuilder.withPayload(orderParameter)
                    .setHeader(MessageConst.PROPERTY_KEYS, UUID.randomUUID().toString())
                    .setHeader("status", UUID.randomUUID().toString())
                    .build();

            rocketMQTemplate.sendMessageInTransaction("order:order",message,orderDto);
            return result;
        }
        return Result.build().setMsg("传入参数异常,下单失败");
    }

}
