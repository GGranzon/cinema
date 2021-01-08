package com.woniuxy.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MovieDto {
    private String movieName;
    private String type;
    private String timeLength;
    private String director;
    private String description;
    private String image;
}
