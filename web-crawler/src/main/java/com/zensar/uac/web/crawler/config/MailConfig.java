package com.zensar.uac.web.crawler.config;

import com.zensar.uac.web.crawler.exception.MessageBuilderException;
import com.zensar.uac.web.crawler.pojo.EmailConfig;
import com.zensar.uac.web.crawler.services.EmailService;
import com.zensar.uac.web.crawler.services.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

/**
 * Created by srikant.singh on 10/12/2016.
 * Purpose of the class: Mail configuration
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Value("${mail.email.host}")
    private String host;

    @Value("${mail.email.port}")
    private String port;

    @Value("${mail.email.tls}")
    private String tls;

    @Value("${mail.email.ssl}")
    public String ssl;

    @Value("${mail.email.from}")
    private String from;

    @Value("${mail.email.bounceAddress}")
    private String bounceAddress;

    @Value("${mail.email.contentType}")
    private String contentType;

    @Value("${mail.email.userName}")
    private String username;

    @Value("${mail.email.password}")
    private String pass;

    @Value("${mail.email.smtpAuth}")
    private String smtpAuth;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public EmailConfig emailConfig() {
        EmailConfig emailConfig = new EmailConfig();
        emailConfig.setFrom(from);
        emailConfig.setHost(host);
        emailConfig.setPort(port);
        emailConfig.setSsl(ssl);
        emailConfig.setBounceAddress(bounceAddress);
        emailConfig.setTls(tls);
        emailConfig.setContentType(contentType);
        emailConfig.setUserName(username);
        emailConfig.setPassKey(pass);
        emailConfig.setSmtpAuth(smtpAuth);
        return emailConfig;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public EmailService emailService() throws MessageBuilderException {
        return new EmailServiceImpl(emailConfig());
    }
}
