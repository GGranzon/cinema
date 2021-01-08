package com.woniuxy.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MovieSessionDto {

    private Integer movieHallCode;

    private String date;

    private String startTime;
}
