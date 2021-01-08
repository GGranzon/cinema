package com.woniuxy.component;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//@Component
//@Aspect
//public class MovieInterceptor {
//
//    @Resource
//    private StringRedisTemplate redisTemplate;
//
//    @Pointcut("execution(* com.woniuxy.service.impl.*.*(..))")
//    public void pointCut(){}
//
//    @AfterThrowing("pointCut()")
//    public void AfterThrowing(){
////        redisTemplate.opsForZSet().add("num:",1,)
//
//    }
//}
