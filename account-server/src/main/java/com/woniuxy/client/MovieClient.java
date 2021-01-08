package com.woniuxy.client;

import com.woniuxy.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-server")
public interface MovieClient {

    @GetMapping("showMovie")
    public Result showMovie(@RequestParam("url") String url);
}
