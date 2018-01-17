package com.zensar.uac.web.crawler.pojo;

import java.util.Map;
import java.util.Set;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: POJO for URl Cralwer Report
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class URLCrawlerReport {
    private int domainAsciiLinkCount;
    private int domainAsciiUtfLinkCount;
    private int domainLinkCount;
    private int inactiveAsciiLinkCount;
    private int inactiveLinkCount;
    private int extAsciiLinkCount;
    private int extLinkCounnt;

    private int totalLinkCount;
    private Map<String, Integer> urlTOTotalLink;

    //email fields
    private Set<String> asciiEmails;
    private Set<String> emails;
    private int formFieldCount;

    private Set<String> domainLinks;

    public URLCrawlerReport() {
    }

    public URLCrawlerReport(int domainAsciiLinkCount, int domainAsciiUtfLinkCount, int domainLinkCount,
                            int extAsciiLinkCount, int extLinkCounnt, int inactiveAsciiLinkCount,
                            int inactiveLinkCount, int totalLinkCount,
                            Map<String, Integer> urlTOTotalLink, Set<String> asciiEmails, Set<String> emails, int formFieldCount, Set<String> domainLinks) {
        this.domainAsciiLinkCount = domainAsciiLinkCount;
        this.domainAsciiUtfLinkCount = domainAsciiUtfLinkCount;
        this.domainLinkCount = domainLinkCount;
        this.extAsciiLinkCount = extAsciiLinkCount;
        this.extLinkCounnt = extLinkCounnt;
        this.inactiveAsciiLinkCount = inactiveAsciiLinkCount;
        this.inactiveLinkCount = inactiveLinkCount;
        this.totalLinkCount = totalLinkCount;
        this.urlTOTotalLink = urlTOTotalLink;

        //emails
        this.asciiEmails = asciiEmails;
        this.emails = emails;
        this.formFieldCount = formFieldCount;
        this.domainLinks = domainLinks;
    }

    public Map<String, Integer> getUrlTOTotalLink() {
        return urlTOTotalLink;
    }

    public int getDomainLinkCount() {
        return domainLinkCount;
    }

    public int getExtLinkCounnt() {
        return extLinkCounnt;
    }

    public int getDomainAsciiLinkCount() {
        return domainAsciiLinkCount;
    }

    public int getExtAsciiLinkCount() {
        return extAsciiLinkCount;
    }

    public int getInactiveLinkCount() {
        return inactiveLinkCount;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Set<String> getAsciiEmails() {
        return asciiEmails;
    }

    public void setAsciiEmails(Set<String> asciiEmails) {
        this.asciiEmails = asciiEmails;
    }

    public int getFormFieldCount() {
        return formFieldCount;
    }

    public void setFormFieldCount(int formFieldCount) {
        this.formFieldCount = formFieldCount;
    }

    public int getInactiveAsciiLinkCount() {
        return inactiveAsciiLinkCount;
    }

    public void setInactiveAsciiLinkCount(int inactiveAsciiLinkCount) {
        this.inactiveAsciiLinkCount = inactiveAsciiLinkCount;
    }

    public void setDomainAsciiLinkCount(int domainAsciiLinkCount) {
        this.domainAsciiLinkCount = domainAsciiLinkCount;
    }

    public void setDomainLinkCount(int domainLinkCount) {
        this.domainLinkCount = domainLinkCount;
    }

    public void setInactiveLinkCount(int inactiveLinkCount) {
        this.inactiveLinkCount = inactiveLinkCount;
    }

    public void setExtAsciiLinkCount(int extAsciiLinkCount) {
        this.extAsciiLinkCount = extAsciiLinkCount;
    }

    public void setExtLinkCounnt(int extLinkCounnt) {
        this.extLinkCounnt = extLinkCounnt;
    }

    public int getTotalLinkCount() {
        return totalLinkCount;
    }

    public void setTotalLinkCount(int totalLinkCount) {
        this.totalLinkCount = totalLinkCount;
    }

    public void setUrlTOTotalLink(Map<String, Integer> urlTOTotalLink) {
        this.urlTOTotalLink = urlTOTotalLink;
    }

    public Set<String> getDomainLinks() {
        return domainLinks;
    }

    public int getDomainAsciiUtfLinkCount() {
        return domainAsciiUtfLinkCount;
    }

    public void setDomainAsciiUtfLinkCount(int domainAsciiUtfLinkCount) {
        this.domainAsciiUtfLinkCount = domainAsciiUtfLinkCount;
    }
}