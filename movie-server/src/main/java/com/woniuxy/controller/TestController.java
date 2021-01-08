package com.woniuxy.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test1")
    @SentinelResource(value = "test1",blockHandler = "exceptionHandler",fallback = "test1Fallback")
    public String test1(long s){
//        int i = 1/0;
        return String.format("Hello at %d",s);
    }
    //Fallback 函数，函数签名与原函数一致或 加一个Throwable类型的参数
    public String test1Fallback(String s){
        return String.format("Halooooo %d",s);
    }

    //block异常处理函数,参数最后多一个 BlockException 其余与原函数一致
    public String exceptionHandler(long s, BlockException ex){
        ex.printStackTrace();
        return  "Oops,error occurred at" + s;
    }

}
