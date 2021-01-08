package com.woniuxy.service.impl;

import com.woniuxy.annotation.RedissonLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Service
public class LockAdvice {

    @Resource
    private RedissonClient redissonClient;

    @Around("execution(* *..service..*.*(..)) && @annotation(com.woniuxy.annotation.RedissonLock)")
    public Object lock(ProceedingJoinPoint point){
        RLock lock = null;
        try {
            MethodSignature methodSignature = (MethodSignature)point.getSignature();
            Method method = methodSignature.getMethod();
            RedissonLock redissonLock = method.getDeclaredAnnotation(RedissonLock.class);
            lock = redissonClient.getLock(redissonLock.lockKey());
            boolean b = lock.tryLock(redissonLock.lockTimeout(),redissonLock.timeout(), TimeUnit.SECONDS);
            if(b){
                return point.proceed();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
        return null;
    }
}
