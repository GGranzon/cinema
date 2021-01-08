package com.woniuxy.parameter;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MovieSessionParam {

    private Integer movieHallCode;

    private String date;

    private String startTime;
}
