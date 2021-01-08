package com.woniuxy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.Arrange;
import com.woniuxy.domain.MovieHall;
import com.woniuxy.dto.ArrangeDto;
import com.woniuxy.dto.MovieSessionDto;
import com.woniuxy.dto.OrderDto;
import com.woniuxy.dto.Result;

public interface ArrangeService extends IService<Arrange> {
    //上架排片
    Result arrangeMovie(ArrangeDto arrangeDto);
    //获取具体影厅和场次的排片信息
    Result getArrange(MovieSessionDto movieSessionDto);
    //下单后更改商品状态
    Result changeStatus(OrderDto orderDto);
}
