package com.jackie.matchingtradingsystemserver.Response;


/*
 * 通用返回格式
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BusiResultResponse {

    public static final int SUCCESS = 0;

    public static final int  RELOGIN = 1;

    public static final int FAIL = 2;


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
