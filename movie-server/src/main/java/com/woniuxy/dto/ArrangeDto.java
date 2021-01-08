package com.woniuxy.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ArrangeDto {
    private Integer movieHallCode;
    private String date;
    private String startTime;
    private String endTime;
    private Double price;
    private String movieName;
}
