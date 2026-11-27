package com.jackie.matchingtradingsystemserver.controller;

import com.jackie.matchingtradingsystemserver.Response.BusiResultResponse;
import com.jackie.matchingtradingsystemserver.Response.CaptchaResponse;
import com.jackie.matchingtradingsystemserver.cache.BusiCacheType;
import com.jackie.matchingtradingsystemserver.cache.RedisStringCache;
import com.jackie.matchingtradingsystemserver.model.Account;
import com.jackie.matchingtradingsystemserver.model.OrderInfo;
import com.jackie.matchingtradingsystemserver.model.PosiInfo;
import com.jackie.matchingtradingsystemserver.model.TradeInfo;
import com.jackie.matchingtradingsystemserver.service.AccountService;
import com.jackie.matchingtradingsystemserver.service.OrderService;
import com.jackie.matchingtradingsystemserver.util.Captcha;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thirdpart.uuid.GudyUuid;

import java.util.List;

@RestController
@RequestMapping("/api")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/balance")
    public BusiResultResponse balanceQuery(@RequestParam long uid) throws Exception{
        long balance = orderService.getBalance(uid);
        return new BusiResultResponse(balance);
    }

    @RequestMapping("/posiInfo")
    public BusiResultResponse posiQuery(@RequestParam long uid) throws Exception{
        List<PosiInfo> posiInfos = orderService.getPosiInfo(uid);
        return new BusiResultResponse(posiInfos);

    }

    @RequestMapping("/orderInfo")
    public BusiResultResponse orderQuery(@RequestParam long uid) throws Exception{
        List<OrderInfo> orderInfos = orderService.getOrderInfo(uid);
        return new BusiResultResponse(orderInfos);
    }

    @RequestMapping("/tradeInfo")
    public BusiResultResponse tradeQuery(@RequestParam long uid) throws Exception{
        List<TradeInfo> tradeInfos = orderService.getTradeInfo(uid);
        return new BusiResultResponse(tradeInfos);
    }

}
