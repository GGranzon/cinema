package com.woniuxy.service.impl;

import com.woniuxy.annotation.RedissonLock;
import com.woniuxy.component.MyRedisLock;
import com.woniuxy.domain.Order;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service("orderService")
public class OrderService {
    @Resource
    private MyRedisLock myRedisLock;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;

    public void addOrder(Order order) {
        System.out.println("开始执行order业务方法" + ":" + order);
    }

    public  void add() {
        RLock lock = null;
        try {
//            boolean lock = myRedisLock.lock("lock");
            lock = redissonClient.getLock("lock");
            boolean b = lock.tryLock(20,10, TimeUnit.SECONDS);
            if (b) {
                Integer i = Integer.parseInt(stringRedisTemplate.opsForValue().get("i"));
                if (i < 1000) {
                    i++;
                    stringRedisTemplate.opsForValue().set("i", i + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            myRedisLock.unlock("lock");
            lock.unlock();
        }
    }
    @RedissonLock(lockKey = "lock")
    public void addI(){
        Integer i = Integer.parseInt(stringRedisTemplate.opsForValue().get("i"));
        if (i < 1000) {
            i++;
            stringRedisTemplate.opsForValue().set("i", i + "");
        }
    }

    public  void add2() {
        RLock lock = null;
        try {
//            boolean b = myRedisLock.lock("lock");
            boolean b = lock.tryLock(20,10, TimeUnit.SECONDS);
            if(b){
                System.out.println("在方法add2中调用add");
                add();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            myRedisLock.unlock("lock");
            lock.unlock();
        }
    }

}
