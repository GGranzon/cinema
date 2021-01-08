package com.woniuxy.service.impl;

import com.woniuxy.client.MovieFeignClient;
import com.woniuxy.dto.OrderDto;
import com.woniuxy.parameter.OrderParameter;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Service
@RocketMQMessageListener(topic = "order" , consumerGroup = "movie")
public class MovieMessageConsumer implements RocketMQListener<OrderDto> {

    @Resource
    private MovieFeignClient movieFeignClient;

    @Override
    public void onMessage(OrderDto orderDto) {
        System.out.println("消费者开始消费消息");
        System.out.println(orderDto);
        if(!ObjectUtils.isEmpty(orderDto)){
            OrderParameter orderParameter = new OrderParameter();
            BeanUtils.copyProperties(orderDto,orderParameter);
            movieFeignClient.changeStatus(orderParameter);
        }

    }
}
