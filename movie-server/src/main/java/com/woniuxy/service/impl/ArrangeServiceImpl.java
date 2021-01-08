package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.domain.Arrange;
import com.woniuxy.domain.MovieHall;
import com.woniuxy.dto.ArrangeDto;
import com.woniuxy.dto.MovieSessionDto;
import com.woniuxy.dto.OrderDto;
import com.woniuxy.dto.Result;
import com.woniuxy.mapper.ArrangeMapper;
import com.woniuxy.service.ArrangeService;
import com.woniuxy.service.MovieHallService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service("arrangeService")
public class ArrangeServiceImpl extends ServiceImpl<ArrangeMapper, Arrange> implements ArrangeService {
    @Resource
    private ArrangeService arrangeService;
    @Resource
    private MovieHallService movieHallService;


    @Override
    public Result arrangeMovie(ArrangeDto arrangeDto) { ;
        if(!ObjectUtils.isEmpty(arrangeDto)){
            Arrange arrange = new Arrange();
            BeanUtils.copyProperties(arrangeDto,arrange);
            Integer movieHallCode = arrangeDto.getMovieHallCode();
            if(!ObjectUtils.isEmpty(movieHallCode)){
                //查询影厅座位
                MovieHall movieHall = movieHallService.getOne(new QueryWrapper<MovieHall>().eq("movie_hall_code", movieHallCode));
                System.out.println(movieHall);
                if(!ObjectUtils.isEmpty(movieHall)){
                    arrange.setVersion(movieHall.getVersion());
                    Result result = null;
                    for (int i = 0; i < movieHall.getRow(); i++) {
                        for (int j = 0; j < movieHall.getColumnn(); j++) {
                            arrange.setColumnn(j+1).setRow(i+1);
                            if (arrangeService.save(arrange)) {
                                 result =  Result.build().setMsg("排片成功").setCode(200);
                            }
                        }
                    }
                    return result;
                }
            }
        }
        return Result.build().setMsg("排片失败");
    }

    //获取具体的排片信息
    @Override
    public Result getArrange(MovieSessionDto movieSessionDto) {
        if(!ObjectUtils.isEmpty(movieSessionDto)){
            List<Arrange> arrange = arrangeService.list(new QueryWrapper<Arrange>()
                    .eq("movie_hall_code", movieSessionDto.getMovieHallCode())
                    .eq("start_time", movieSessionDto.getStartTime())
                    .eq("date",movieSessionDto.getDate())
                    .eq("status","未售")
            );
            if(!ObjectUtils.isEmpty(arrange)){

                return Result.build().setMsg("获取具体场次的排片信息成功").setCode(200).setData(arrange);
            }
        }
        return null;
    }

    //下单后 更改状态为待付款
    @Override
    public Result changeStatus(OrderDto orderDto) {
        if(!ObjectUtils.isEmpty(orderDto)){
            //查询出已下单的商品
            Arrange arrange = arrangeService.getOne(new QueryWrapper<Arrange>()
                    .eq("movie_hall_code", orderDto.getMovieHallCode())
                    .eq("date", orderDto.getDate())
                    .eq("start_time", orderDto.getStartTime())
                    .eq("row", orderDto.getRow())
                    .eq("columnn", orderDto.getColumnn())
            );
            //更改状态
            if(!ObjectUtils.isEmpty(arrange)){
                arrange.setStatus("待付款");
                if (arrangeService.updateById(arrange)) {
                    return Result.build().setMsg("商品状态更改成功").setData(arrange).setCode(200);
                }
            }
        }
        return Result.build().setMsg("商品状态更改失败");
    }
}
