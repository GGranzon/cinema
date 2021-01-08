package com.woniuxy.client;

import com.woniuxy.dto.Result;
import com.woniuxy.parameter.OrderParameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "movie-server")
public interface MovieFeignClient {

    @GetMapping("/movie/changeStatus")
    public Result changeStatus(@SpringQueryMap OrderParameter orderParameter);


}
