package com.sso.zjx.crm.filter;

import com.sso.zjx.crm.config.MyProperties;
import com.sso.zjx.crm.util.HttpClientUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter implements Filter{

    MyProperties myProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        if(myProperties==null){
            myProperties = (MyProperties) factory.getBean("myProperties");
        }

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        //1.判断是否有局部会话
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        if(isLogin != null && isLogin){
            chain.doFilter(request,response);
            return;
        }
        //判断地址栏中是否有携带token参数
        String token = req.getParameter("token");
        if(!StringUtils.isEmpty(token)){
            //token信息不为null,说明地址中包含了token,拥有令牌
            //判断token信息是否由认证中心产生的
            String httpUrl = myProperties.getSsoUrl()+"/verify";
            Map<String,String> params = new HashMap<String, String>();
            params.put("token",token);
            params.put("clientLogOutUrl",getClientLogOutUrl());
            params.put("jsessionId",session.getId());
            String res = HttpClientUtil.doPost(httpUrl,params);
            if("true".equals(res)){
                //如果返回的是true,说明这个token是由同一认证中心所产生的，创建局部的会话
                session.setAttribute("isLogin",true);
                chain.doFilter(request,response);
                return;
            }
        }

        //没有局部会话，重定向到统一认证中心，检查是否有其他的系统已经登录
        // http://www.sso.com:8083/checkLogin?redirectUrl=http://www.crm.com:8081

        String url = myProperties.getSsoUrl()+"/checkLogin?redirectUrl="+getRedirectUrl(req);
        resp.sendRedirect(url);
    }

    private String getRedirectUrl(HttpServletRequest request){
        return myProperties.getClientUrl()+request.getRequestURI();
    }

    private String getClientLogOutUrl(){
        return myProperties.getClientUrl()+"/logOut";
    }
}
