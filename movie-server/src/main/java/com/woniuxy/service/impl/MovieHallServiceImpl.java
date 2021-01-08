package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.domain.MovieHall;
import com.woniuxy.mapper.MovieHallMapper;
import com.woniuxy.service.MovieHallService;
import org.springframework.stereotype.Service;

@Service("movieHallService")
public class MovieHallServiceImpl extends ServiceImpl<MovieHallMapper, MovieHall> implements MovieHallService {
}
