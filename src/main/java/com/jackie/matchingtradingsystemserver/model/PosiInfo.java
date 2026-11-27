package com.jackie.matchingtradingsystemserver.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class PosiInfo {

    private int id;

    private long uid;

    private int code;

    /*
    不是t_posi 表字段，需要关联查询查出
    */
    private String name;

    private long cost;

    private long count;




}
