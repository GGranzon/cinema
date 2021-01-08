package com.woniuxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.domain.User;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.UserDto;

public interface UserService extends IService<User> {
    //注册
    Result register(UserDto userDto);
}
