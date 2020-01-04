package com.sso.zjx.crm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyProperties {

    @Value("${ssoUrl}")
    private String ssoUrl;
    @Value("${clientUrl}")
    private String clientUrl;

    public String getSsoUrl() {
        return ssoUrl;
    }

    public void setSsoUrl(String ssoUrl) {
        this.ssoUrl = ssoUrl;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }
}
