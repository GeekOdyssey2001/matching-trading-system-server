package com.jackie.matchingtradingsystemserver.service;

import com.jackie.matchingtradingsystemserver.model.OrderInfo;
import com.jackie.matchingtradingsystemserver.model.PosiInfo;
import com.jackie.matchingtradingsystemserver.model.TradeInfo;

import java.util.List;

public interface OrderService {


    //查资金
    Long getBalance(long uid);

    //查持仓
    List<PosiInfo> getPosiInfo(long uid);

    //查委托
    List<OrderInfo> getOrderInfo(long uid);

    //查成交
    List<TradeInfo> getTradeInfo(long uid);







}
