package com.woniuxy.component;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Component
public class MyRedisLock {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    //锁持续时间
    private Integer lockedTimeout = 20;
    //上锁过程时间限制
    private Integer lockingTimeout = 20;

    //重入锁实现
    private ThreadLocal<String> threadLocal;
    //链路节点控制
    private ThreadLocal<Integer> threadLocalVersion;



    //上锁
    public boolean lock(String key){
        if(threadLocal.get() != null){
            //如果已经加锁，那么链路层级加 1
            threadLocalVersion.set(threadLocalVersion.get() + 1);
            return true;
        }

        String value = UUID.randomUUID().toString();
        Boolean b = false;
        long time = System.currentTimeMillis();
        while (!(b = stringRedisTemplate.opsForValue().setIfAbsent(key,value,lockedTimeout, TimeUnit.SECONDS))) {
            if(System.currentTimeMillis() - time >= lockingTimeout * 1000){
                break;
            }
            //缓解加锁对cpu资源的占用
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //第一次加锁时，设置层级为1，并保存自己的锁的value
        if(b){
            threadLocalVersion.set(1);
            threadLocal.set(value);
        }
        return b;
    }

    //解锁
    public void unlock(String key){
        if(!StringUtils.isEmpty(key)){
            String v = stringRedisTemplate.opsForValue().get(key);
            String value = threadLocal.get();
            if(value != null && v.equals(value)){
                Integer i = threadLocalVersion.get();
                if(i != null && i <= 1){
                    stringRedisTemplate.delete(key);
                    threadLocal.set(null);
                    threadLocalVersion.set(null);
                }else {
                    threadLocalVersion.set(threadLocalVersion.get() - 1);
                }
            }
        }
    }

}
