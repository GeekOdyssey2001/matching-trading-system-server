package com.jackie.matchingtradingsystemserver.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrderInfo {

    private int id;

    private long uid;

    private int code;

    /*
    不是t_order 表字段，需要关联查询查出
    */
    private String name;

    private int direction;

    private int type;

    private long price;

    private long ocount;

    private int status;

    private String date;

    private String time;

}
