package com.jackie.matchingtradingsystemserver.controller;


import com.jackie.matchingtradingsystemserver.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class Hello {

//    @Autowired
//    private DbUtil dbUtil; 单例模式不需要@Autowired，省了不少代码

    @Autowired
    private StringRedisTemplate template;

    @RequestMapping("/hello")
    public String hello(){
        return "hello world!";
    }

    @RequestMapping("/dbTest")
    public String dbTest(){
        return "" + DbUtil.getId();
    }

    @RequestMapping("/cacheTest")
    public String cacheTest(){
        template.opsForValue().set("test:hello","world");
        return template.opsForValue().get("test:hello");
    }



}
