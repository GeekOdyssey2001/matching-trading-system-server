package com.jackie.matchingtradingsystemserver.util;

import com.google.common.collect.ImmutableMap;
import com.jackie.matchingtradingsystemserver.model.Account;
import jakarta.annotation.PostConstruct;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 和数据所有的交互都由这个工具类提供
 */
@Component //spring 启动时会反射创建SqlSessionTemplate类，并且把SqlSessionTemplate以成员变量注到DbUtil中
public class DbUtil {

    private static DbUtil dbUtil = null;

    private DbUtil(){

    }

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
            this.sqlSessionTemplate = sqlSessionTemplate;
    }

    private SqlSessionTemplate getSqlSessionTemplate(){
        return this.sqlSessionTemplate;
    }

    @PostConstruct //在当前类构造完成后立即执行
    private void init(){
        dbUtil = new DbUtil();
        dbUtil.setSqlSessionTemplate(this.sqlSessionTemplate);
    }


    //DbUtil.getId();
    public static long getId(){
        Long res = dbUtil.getSqlSessionTemplate().selectOne("" +
                "testMapper.queryBalance"); //结果是对象，需要空判断，确保getId拿到的是非空值，减少上层的空值
        if(res == null){
            return -1;
        }else{
            return res;
        }
    }

    ////////////////////////身份认证////////////////////////
    public static Account queryAccount(long uid, String password){

        return dbUtil.getSqlSessionTemplate().selectOne(
                "userMapper.queryAccount",
                ImmutableMap.of("Uid",uid,"Password",password)
        );
    }

    public static void updateLoginTime(Long uid,String nowDate,String nowTime){
                dbUtil.getSqlSessionTemplate().update(
          "userMapper.updateAccountLoginTime",
                ImmutableMap.of(
                    "Uid",uid,
                    "Modify",nowDate,
                    "ModifyTime",nowTime
            ));
    }





}
