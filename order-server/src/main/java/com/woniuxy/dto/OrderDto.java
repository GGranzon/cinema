package com.woniuxy.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderDto {
    private String tel;
    private String movieName;
    private Integer row;
    private Integer columnn;
    private Integer movieHallCode;
    private String startTime;
    private Double price;
    private String date;
}
