package com.sso.zjx.crm.config;

import com.sso.zjx.crm.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MyConfig {

    @Autowired
    MyProperties myProperties;

    @Bean
    public FilterRegistrationBean loginFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        LoginFilter loginFilter = new LoginFilter();
        registrationBean.setFilter(loginFilter);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        return registrationBean;
    }

}
