package com.jackie.matchingtradingsystemserver.service;


import com.jackie.matchingtradingsystemserver.model.OrderInfo;
import com.jackie.matchingtradingsystemserver.model.PosiInfo;
import com.jackie.matchingtradingsystemserver.model.TradeInfo;
import com.jackie.matchingtradingsystemserver.util.DbUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Override
    public Long getBalance(long uid) {
        return DbUtil.getBalance(uid);
    }

    @Override
    public List<PosiInfo> getPosiInfo(long uid) {
        return DbUtil.getPosiList(uid);
    }

    @Override
    public List<OrderInfo> getOrderInfo(long uid) {
        return DbUtil.getOrderList(uid);
    }

    @Override
    public List<TradeInfo> getTradeInfo(long uid) {
        return DbUtil.getTradeList(uid);
    }
}
