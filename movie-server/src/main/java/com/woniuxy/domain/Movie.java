package com.woniuxy.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName(value = "t_movie")
public class Movie implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "movie_name")
    private String movieName;
    private String type;
    @TableField(value = "time_length")
    private String timeLength;
    @TableField(value = "director")
    private String director;
    private String description;
    private String image;
    private String status;
}
