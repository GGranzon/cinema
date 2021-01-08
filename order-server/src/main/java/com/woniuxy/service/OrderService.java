package com.woniuxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.Order;
import com.woniuxy.dto.OrderDto;
import com.woniuxy.dto.Result;

public interface OrderService extends IService<Order> {

    Result order(OrderDto orderDto);
}
