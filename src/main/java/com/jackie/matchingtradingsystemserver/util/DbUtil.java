package com.jackie.matchingtradingsystemserver.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.jackie.matchingtradingsystemserver.cache.BusiCacheType;
import com.jackie.matchingtradingsystemserver.cache.RedisStringCache;
import com.jackie.matchingtradingsystemserver.model.Account;
import com.jackie.matchingtradingsystemserver.model.OrderInfo;
import com.jackie.matchingtradingsystemserver.model.PosiInfo;
import com.jackie.matchingtradingsystemserver.model.TradeInfo;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

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
                ImmutableMap.of("UId",uid,"Password",password)
        );
    }

    public static void updateLoginTime(Long uid,String nowDate,String nowTime){
                dbUtil.getSqlSessionTemplate().update(
          "userMapper.updateAccountLoginTime",
                ImmutableMap.of(
                    "UId",uid,
                    "ModifyDate",nowDate,
                    "ModifyTime",nowTime
            ));
    }


    public static int updatePwd(long uid, String oldPwd, String newPwd){
                return dbUtil.getSqlSessionTemplate().update(
                    "userMapper.updatePwd",
                        ImmutableMap.of(
                                "UId",uid,
                                "Newpwd",newPwd,
                                "OldPwd",oldPwd
                        ));
    }

    ////////////////////////资金类////////////////////////
    public static long getBalance(long uid){
        Long result = dbUtil.getSqlSessionTemplate()
                .selectOne("orderMapper.queryBalance",
                        ImmutableMap.of("UId",uid));
        if(result == null){
            return -1;
        }else {
            return result;
        }
    }

    /**
     * 持仓类、委托类和成交类QPS高，需要把数据缓存到redis
     *
     */

    ////////////////////////持仓类////////////////////////
    public static List<PosiInfo> getPosiList(long uid){
        //查缓存
        String suid = Long.toString(uid);
        String posiStr = RedisStringCache.get(suid, BusiCacheType.POSI);
        if(StringUtils.isEmpty(posiStr)){
            //
            List<PosiInfo> tmp = dbUtil.getSqlSessionTemplate().selectList(
                    "orderMapper.queryPosi",
                    ImmutableMap.of("UId",uid));
            List<PosiInfo> result = CollectionUtils.isEmpty(tmp) ? Lists.newArrayList() : tmp;
            //更新缓存
            RedisStringCache.cache(suid,JsonUtil.toJson(result),BusiCacheType.POSI);
            return result;
        }else{
            //查到，命中缓存
            return JsonUtil.fromJsonArr(posiStr,PosiInfo.class);
        }
    }

    ////////////////////////委托类////////////////////////
    public static List<OrderInfo> getOrderList(long uid){
        //查缓存
        String suid = Long.toString(uid);
        String OrderStr = RedisStringCache.get(suid, BusiCacheType.ORDER);
        if(StringUtils.isEmpty(OrderStr)){
            //
            List<OrderInfo> tmp = dbUtil.getSqlSessionTemplate().selectList(
                    "orderMapper.queryOrder",
                    ImmutableMap.of("UId",uid));
            List<OrderInfo> result = CollectionUtils.isEmpty(tmp) ? Lists.newArrayList() : tmp;
            //更新缓存
            RedisStringCache.cache(suid,JsonUtil.toJson(result),BusiCacheType.ORDER);
            return result;
        }else{
            //查到，命中缓存
            return JsonUtil.fromJsonArr(OrderStr,OrderInfo.class);
        }
    }

    ////////////////////////成交类////////////////////////
    public static List<TradeInfo> getTradeList(long uid){
        //查缓存
        String suid = Long.toString(uid);
        String tradeStr = RedisStringCache.get(suid, BusiCacheType.TRADE);
        if(StringUtils.isEmpty(tradeStr)){
            //
            List<TradeInfo> tmp = dbUtil.getSqlSessionTemplate().selectList(
                    "orderMapper.queryTrade",
                    ImmutableMap.of("UId",uid));
            List<TradeInfo> result = CollectionUtils.isEmpty(tmp) ? Lists.newArrayList() : tmp;
            //更新缓存
            RedisStringCache.cache(suid,JsonUtil.toJson(result),BusiCacheType.TRADE);
            return result;
        }else{
            //查到，命中缓存
            return JsonUtil.fromJsonArr(tradeStr,TradeInfo.class);
        }
    }

}
