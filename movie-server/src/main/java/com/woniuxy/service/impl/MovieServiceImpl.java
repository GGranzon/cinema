package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.domain.Movie;
import com.woniuxy.dto.MovieDto;
import com.woniuxy.dto.Result;
import com.woniuxy.mapper.MovieMapper;
import com.woniuxy.service.MovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
@Service("movieService")
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {
    @Resource
    private MovieService movieService;

    @Override
    public Result addMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        BeanUtils.copyProperties(movieDto,movie);
        if(!ObjectUtils.isEmpty(movie)){
            if (movieService.save(movie)) {
                return Result.build().setCode(200).setMsg("新增影片成功").setData(movie);
            }
        }
        return Result.build().setMsg("新增影片失败");
    }

    @Override
    public Result showMovie(String url) {
        Movie movie = movieService.getOne(new QueryWrapper<Movie>().eq("image", url));
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movie,movieDto);
        if(!ObjectUtils.isEmpty(movieDto)){
            return Result.build().setCode(200).setMsg("查询影片详情成功").setData(movieDto);
        }
        return Result.build().setMsg("查询影片失败");
    }
}
