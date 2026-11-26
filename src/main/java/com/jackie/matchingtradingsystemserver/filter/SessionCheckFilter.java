package com.jackie.matchingtradingsystemserver.filter;

import com.google.common.collect.Sets;
import com.jackie.matchingtradingsystemserver.service.AccountService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class SessionCheckFilter implements Filter {

    private AccountService accountService;

    private Set<String> whiteRootPaths = Sets.newHashSet(
            "login","msgsocket","test"
    ); //一般从配置中心拉取

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //解决ajax跨域问题
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        //请求地址校验
        // http://localhost:8090/login/pwdsetting
        // /login/pwdsetting
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String path = httpRequest.getRequestURI();
        String [] split = path.split("/");
        if(split.length < 2){
            httpRequest.getRequestDispatcher(
                    "/login/loginfail"
            ).forward(request,response);
        }else{
            if(!whiteRootPaths.contains(split[1])){
                //不在白名单，验证token是否合法
                if(accountService.accountExistInCache( httpRequest.getParameter("token"))){
                    chain.doFilter(request,response); //放行请求
                }else{
                    httpRequest.getRequestDispatcher("/login/loginfail").forward(request,response);
                }
            }else{
                //在白名单，直接放行
                chain.doFilter(request,response); //放行请求
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
