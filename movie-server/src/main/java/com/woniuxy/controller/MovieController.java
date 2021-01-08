package com.woniuxy.controller;

import com.woniuxy.dto.*;
import com.woniuxy.parameter.ArrangeParameter;
import com.woniuxy.parameter.MovieParameter;
import com.woniuxy.parameter.MovieSessionParam;
import com.woniuxy.parameter.OrderParameter;
import com.woniuxy.service.ArrangeService;
import com.woniuxy.service.MovieService;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class MovieController {

    @Resource
    private MovieService movieService;
    @Resource
    private ArrangeService arrangeService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    //添加影片
    @GetMapping("addMovie")
    public Result addMovie(MovieParameter movieParameter) {
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movieParameter, movieDto);
        if (!ObjectUtils.isEmpty(movieDto)) {
            Result result = movieService.addMovie(movieDto);
            return result;
        }
        return Result.build().setMsg("参数为空，新增影片失败");
    }

    //上架排片
    @GetMapping("arrangeMovie")
    public Result arrangeMovie(ArrangeParameter arrangeParameter) {
        ArrangeDto arrangeDto = new ArrangeDto();
        BeanUtils.copyProperties(arrangeParameter, arrangeDto);
        if (!ObjectUtils.isEmpty(arrangeDto)) {
            Result result = arrangeService.arrangeMovie(arrangeDto);
            return result;
        }
        return Result.build();
    }

    //展示详情并记录浏览量
    @GetMapping("showMovie")
    public Result showMovie(String url, HttpServletRequest request,String addr) {
//        String token = request.getHeader("token");
//        System.out.println(token+"--------");
        Result result = movieService.showMovie(url);

        //发送一个消息 记录浏览量
        MovieDto movie = (MovieDto) result.getData();
        if (!ObjectUtils.isEmpty(movie)) {
            WatchNum watchNum = new WatchNum();
            watchNum.setMovieName(movie.getMovieName());
            watchNum.setAddr(addr);
            System.out.println(watchNum+"------");
            Message<WatchNum> message = MessageBuilder.withPayload(watchNum)
                    .setHeader(MessageConst.PROPERTY_KEYS, UUID.randomUUID().toString())
                    .build();
            rocketMQTemplate.syncSend("cinema:description", message);
        }


        return result;
    }

    //获得具体影厅和场次的排片信息
    @GetMapping("getArrange")
    public Result getArrange(MovieSessionParam movieSessionParam) {
        if (!ObjectUtils.isEmpty(movieSessionParam)) {
            MovieSessionDto movieSessionDto = new MovieSessionDto();
            BeanUtils.copyProperties(movieSessionParam, movieSessionDto);
            Result result = arrangeService.getArrange(movieSessionDto);
            return result;
        }
        return Result.build().setMsg("传入参数为空，查询失败");
    }

    //下单后更改商品状态为 "待付款"
    @GetMapping("changeStatus")
    public Result changeStatus(OrderParameter orderParameter) {
        if (!ObjectUtils.isEmpty(orderParameter)) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orderParameter, orderDto);
            Result result = arrangeService.changeStatus(orderDto);
            return result;
        }
        return Result.build().setMsg("参数传入有误,状态更改失败");
    }
}
