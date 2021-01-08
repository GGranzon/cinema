package com.woniuxy.service.impl;

import com.woniuxy.dto.Result;
import com.woniuxy.dto.WatchNum;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
@RocketMQMessageListener(topic = "cinema",consumerGroup = "movie")
public class MessageConsumer implements RocketMQListener<WatchNum> {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onMessage(WatchNum watchNum) {
        System.out.println("消费者开始记录浏览量");
        stringRedisTemplate.opsForSet().add(watchNum.getMovieName(),watchNum.getAddr());
        Long size = stringRedisTemplate.opsForSet().size(watchNum.getMovieName());
        stringRedisTemplate.opsForZSet().add("rank",watchNum.getMovieName(),size);
//        Set<ZSetOperations.TypedTuple<String>> rank = stringRedisTemplate.opsForZSet().rangeWithScores("rank", 0, -1);
    }
}
