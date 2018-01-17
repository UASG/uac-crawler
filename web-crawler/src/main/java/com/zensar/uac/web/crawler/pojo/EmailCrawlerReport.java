package com.zensar.uac.web.crawler.pojo;

import java.util.Set;

/**
 * Created by srikant.singh on 29/09/2016.
 * Purpose of the class: POJO for Email Crawler Report
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class EmailCrawlerReport {

    private Set<String> asciiEmails;

    private Set<String> emails;

    private int emailFieldCount;

    public EmailCrawlerReport(Set<String> asciiEmails, Set<String> emails, int emailFieldCount) {
        this.asciiEmails = asciiEmails;
        this.emails = emails;
        this.emailFieldCount = emailFieldCount;
    }

    public Set<String> getAsciiEmails() {
        return asciiEmails;
    }

    public void setAsciiEmails(Set<String> asciiEmails) {
        this.asciiEmails = asciiEmails;
    }

    public int getEmailFieldCount() {
        return emailFieldCount;
    }

    public void setEmailFieldCount(int emailFieldsCount) {
        this.emailFieldCount = emailFieldsCount;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }
}
