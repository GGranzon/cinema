package com.woniuxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.Movie;
import com.woniuxy.dto.MovieDto;
import com.woniuxy.dto.Result;

public interface MovieService extends IService<Movie> {
    //增加影片
    Result addMovie(MovieDto movieDto);

    //展示影片详情
    Result showMovie(String url);
}
