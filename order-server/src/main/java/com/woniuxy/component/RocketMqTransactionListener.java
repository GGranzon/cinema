package com.woniuxy.component;

import com.woniuxy.domain.Order;
import com.woniuxy.dto.OrderDto;
import com.woniuxy.service.OrderService;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Component
@RocketMQTransactionListener
public class RocketMqTransactionListener implements RocketMQLocalTransactionListener {
    @Resource
    private OrderService orderService;

    @Resource
    private  StringRedisTemplate stringRedisTemplate;
    private static final String SUCCESS = "true";
    private static final String FAIL = "false";

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String key =  message.getHeaders().get("status").toString();
        try {
            OrderDto order = null;
            if(!ObjectUtils.isEmpty(o)){
                order = (OrderDto) o;
            }
            System.out.println("开始执行本地事务");
            orderService.order(order);
            stringRedisTemplate.opsForValue().set(key,SUCCESS);
            return RocketMQLocalTransactionState.COMMIT;
        }catch (Exception e){
            stringRedisTemplate.opsForValue().set(key,FAIL);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        System.out.println("执行了回查方法");
        String key = (String) message.getHeaders().get(MessageConst.PROPERTY_KEYS);
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value.equals(SUCCESS))
            return RocketMQLocalTransactionState.COMMIT;
        else
            return RocketMQLocalTransactionState.ROLLBACK;
    }
}
