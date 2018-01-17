package com.zensar.uac.web.crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by KK48481 on 09-08-2017.
 */
@Component
public class HomepageConfig {

    @Value("${server.address}")
    private String address;
    @Value("${server.port}")
    private String port;
    @Value("${server.context-path}")
    private String contextPath;
    @Value("${server.ssl.enabled:false}")
    private boolean sslEnabled;

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getContextPath() {
        return contextPath;
    }
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    public boolean isSslEnabled() {
        return sslEnabled;
    }
    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }
}
