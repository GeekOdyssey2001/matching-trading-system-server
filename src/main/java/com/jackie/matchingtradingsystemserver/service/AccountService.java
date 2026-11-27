package com.jackie.matchingtradingsystemserver.service;

import com.jackie.matchingtradingsystemserver.model.Account;

public interface AccountService {

    //登录逻辑
    Account login(long uid, String password, String captcha, String captchaId) throws Exception;

    //缓存中是否存在已经登录信息
    boolean accountExistInCache(String token);

    boolean logout(String token);

    boolean updatePwd(long uid, String oldPwd, String newPwd);



}
