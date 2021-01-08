package com.woniuxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.woniuxy.mapper")
//@EnableFeignClients(basePackages = "com.woniuxy.client")
public class MovieServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieServerApplication.class, args);
    }

}
