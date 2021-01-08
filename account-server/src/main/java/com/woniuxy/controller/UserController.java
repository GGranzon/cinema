package com.woniuxy.controller;

import com.woniuxy.client.MovieClient;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.UserDto;
import com.woniuxy.parameter.UserParameter;
import com.woniuxy.service.UserService;
import com.woniuxy.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class UserController {
    @Resource
    private MovieClient movieClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserService userService;

    @GetMapping("register")
    public Result register(UserParameter userParameter){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userParameter,userDto);
        return userService.register(userDto);
    }


    @PostMapping("login")
    public Result login(String name,String password){
        if (name.equals("admin") && password.equals("123")){
            HashMap<String, Object> map = new HashMap<>();
            map.put("name","admin");
            String token = JwtUtil.createToken(map);
            return Result.build().setMsg("登录成功").setData(token);
        }
        return Result.build().setMsg("登录失败，未能获取token");
    }

    @GetMapping("test")
    public Result test(){
//        String url = "海报";
//        Result result = movieClient.showMovie(url);
        return Result.build().setMsg("携带token 进行访问");
    }

    @GetMapping("test1")
    public String test1(){
        stringRedisTemplate.opsForValue().set("test","123");
        return "success";
    }

}
