package com.jackie.matchingtradingsystemserver.controller;

import com.jackie.matchingtradingsystemserver.Response.BusiResultResponse;
import com.jackie.matchingtradingsystemserver.Response.CaptchaResponse;
import com.jackie.matchingtradingsystemserver.cache.BusiCacheType;
import com.jackie.matchingtradingsystemserver.cache.RedisStringCache;
import com.jackie.matchingtradingsystemserver.util.Captcha;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thirdpart.uuid.GudyUuid;

@RestController
@RequestMapping("/login")
@Log4j2
public class LoginController {

    @RequestMapping("/captcha")
    public BusiResultResponse captcha() throws Exception{
            //1.生成验证码：120*40，4个字符，噪点+线条
            Captcha captcha = new Captcha(120,40,
                4,10);//长度120，高度40，4个字符，干扰线10条

            //2.将验证码<ID,验证码值>放入缓存
            String uuid = String.valueOf(
                    GudyUuid.getInstance().getUUID()
            );
            RedisStringCache.cache(uuid,captcha.getCode(),
                BusiCacheType.CAPTCHA);

            //3.使用base64编码图片，并返回给前台
            //uuid,base64
        CaptchaResponse response = new CaptchaResponse(uuid,captcha.getBase64ByteStr());
        return new BusiResultResponse(response);



    }

    @RequestMapping("/userLogin")
    public BusiResultResponse login() throws Exception{
        return null;
    }

}
