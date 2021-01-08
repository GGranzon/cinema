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
@TableName(value = "t_movie_hall")
public class MovieHall implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "movie_hall_code")
    private Integer movieHallCode;
    private String version;
    private Integer row;
//    @TableField(value = "'column'")
    private Integer columnn;
}
