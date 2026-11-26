package com.jackie.matchingtradingsystemserver.service;

import com.jackie.matchingtradingsystemserver.cache.BusiCacheType;
import com.jackie.matchingtradingsystemserver.cache.RedisStringCache;
import com.jackie.matchingtradingsystemserver.model.Account;
import com.jackie.matchingtradingsystemserver.util.DbUtil;
import com.jackie.matchingtradingsystemserver.util.JsonUtil;
import com.jackie.matchingtradingsystemserver.util.TimeformatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import thirdpart.uuid.GudyUuid;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService{


    @Override
    public Account login(long uid, String password, String captcha, String captchaId) throws Exception {

            //1.入参的合法性校验
            if(StringUtils.isAnyBlank(password,captcha,captchaId)){
                return null; //应该是抛出异常
            }
            //2.校验验证码
            String captchaCache = RedisStringCache.get(captchaId,
                    BusiCacheType.CAPTCHA);
            if(StringUtils.isEmpty(captchaCache)){
                return null;
            }else if(StringUtils.compareIgnoreCase(captcha,captchaCache) != 0 ){
                return null;
            }
            RedisStringCache.remove(captchaId,BusiCacheType.CAPTCHA);
            //3.数据库用户名和密码校验
            Account account = DbUtil.queryAccount(uid,password);
            if(account == null){
                //1.唯一表示标识作为身份标志
                account.setToken(String.valueOf(GudyUuid.getInstance().getUUID()));
                //2.存入缓存
                RedisStringCache.cache(String.valueOf(
                        account.getToken()),
                        JsonUtil.toJson(account),
                        BusiCacheType.ACCOUNT);
                //3.更新登录时间
                Date date = new Date();
                DbUtil.updateLoginTime(uid,
                        TimeformatUtil.yyyyMMdd(date),
                        TimeformatUtil.hhMMss(date)
                        );
                account.setLastLoginTime(TimeformatUtil.yyyyMMdd(date));
                account.setLastLoginDate(TimeformatUtil.hhMMss(date));
            }
        return account;
    }

    @Override
    public boolean accountExistInCache(String token) {
        return false;
    }
}
