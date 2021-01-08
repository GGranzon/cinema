package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.annotation.RedissonLock;
import com.woniuxy.domain.Order;
import com.woniuxy.dto.OrderDto;
import com.woniuxy.dto.Result;
import com.woniuxy.mapper.OrderMapper;
import com.woniuxy.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderService orderService;
    //下单
    @RedissonLock(lockKey = "orderLock")
    @Override
    public Result order(OrderDto orderDto) {
        if(!ObjectUtils.isEmpty(orderDto)){
            Order order = new Order();
            BeanUtils.copyProperties(orderDto,order);
            if (orderService.save(order)) {
                return Result.build().setMsg("下单成功").setCode(200).setData(orderDto);
            }
        }
        return Result.build().setMsg("下单失败");
    }
}
