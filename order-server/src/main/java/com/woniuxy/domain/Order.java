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
@TableName("t_order")
public class Order implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String tel;
    @TableField(value = "movie_name")
    private String movieName;
    private Integer row;
    private Integer columnn;
    @TableField(value = "movie_hall_code")
    private Integer movieHallCode;
    @TableField(value = "start_time")
    private String startTime;
    private Double price;
    private String date;

}
