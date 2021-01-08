package com.woniuxy.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName(value = "t_arrange")
public class Arrange implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "movie_hall_code")
    private Integer movieHallCode;
    private String date;
    @TableField(value = "start_time")
    private String startTime;
    @TableField(value = "end_time")
    private String endTime;
    private Double price;
    //版本（3d/2d。。。）
    private String version;
    private String status;
    @TableField(value = "movie_name")
    private String movieName;
    private Integer row;
    private Integer columnn;
//    private Integer total;
//    private Integer residue;
//    @TableField(value = "sale_num")
//    private Integer saleNum;
}
