package com.zensar.uac.web.crawler.pojo;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: POJO for Email Configuration
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class EmailConfig {
    private String host;
    private String userName;
    private String passKey;
    private String port;
    private String from;
    private String ssl;
    private String tls;
    private String bounceAddress;
    private String contentType;
    private String smtpAuth;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassKey() {
        return this.passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String isSsl() {
        return this.ssl;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public String isTls() {
        return this.tls;
    }

    public void setTls(String tls) {
        this.tls = tls;
    }

    public String getBounceAddress() {
        return this.bounceAddress;
    }

    public void setBounceAddress(String bounceAddress) {
        this.bounceAddress = bounceAddress;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSmtpAuth() {
        return this.smtpAuth;
    }

    public void setSmtpAuth(String auth) {
        this.smtpAuth = auth;
    }
}
