package com.jackie.matchingtradingsystemserver.controller;

import com.jackie.matchingtradingsystemserver.Response.BusiResultResponse;
import com.jackie.matchingtradingsystemserver.Response.CaptchaResponse;
import com.jackie.matchingtradingsystemserver.cache.BusiCacheType;
import com.jackie.matchingtradingsystemserver.cache.RedisStringCache;
import com.jackie.matchingtradingsystemserver.model.Account;
import com.jackie.matchingtradingsystemserver.service.AccountService;
import com.jackie.matchingtradingsystemserver.util.Captcha;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import thirdpart.uuid.GudyUuid;

@RestController
@RequestMapping("/login")
@Log4j2
public class LoginController {

    @Autowired
    private AccountService accountService;

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
    public BusiResultResponse login(@RequestParam long uid,
                                    @RequestParam String password,
                                    @RequestParam String captcha,
                                    @RequestParam String captchaId) throws Exception{
        Account account = accountService.login(uid,password,captcha,captchaId);
        if(account == null){
            return new BusiResultResponse(BusiResultResponse.FAIL,"用户名密码或者验证码错误",null); //FAIL:代码里不能写数字1，2，要写能易于识别的·内容
        }else{
            return new BusiResultResponse(account);
        }
    }

    @RequestMapping("/loginfail")
    public BusiResultResponse loginFail(){
        return new BusiResultResponse(BusiResultResponse.RELOGIN,"请重新登录",null);
    }

    @RequestMapping("/logout")
    public BusiResultResponse logout(@RequestParam String token){
        accountService.logout(token);
        return new BusiResultResponse(BusiResultResponse.SUCCESS,"退出成功",null);
    }

    @RequestMapping("/pwdUpdate")
    public BusiResultResponse updatePwd(@RequestParam int uid,
                                        @RequestParam String oldPwd,
                                        @RequestParam String newPwd){
        boolean result = accountService.updatePwd(uid,oldPwd,newPwd);
        if(result){
            return new BusiResultResponse(BusiResultResponse.SUCCESS,"修改密码成功",null);
        }else {
            return new BusiResultResponse(BusiResultResponse.FAIL,"修改密码失败",null);
        }

    }

}
