package com.woniuxy.parameter;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MovieParameter {
    private String movieName;
    private String type;
    private String timeLength;
    private String director;
    private String description;
    private String image;
}
