package com.jackie.matchingtradingsystemserver.Response;


/*
 * 通用返回格式
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BusiResultResponse {


    @Getter
    private int code;

    @Getter
    private String message;

    @Getter
    private Object data;

    public BusiResultResponse(Object data){
        this(0,"",data);
    }



}
