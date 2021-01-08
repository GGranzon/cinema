package com.woniuxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.domain.User;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.UserDto;
import com.woniuxy.mapper.UserMapper;
import com.woniuxy.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserService userService;

    @Override
    public Result register(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        if (userService.save(user)) {
            return Result.build().setMsg("注册成功").setData(userDto);
        }
        return Result.build().setMsg("注册失败");
    }
}
