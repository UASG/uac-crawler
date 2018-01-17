package com.zensar.uac.web.crawler.model;

import com.zensar.uac.web.crawler.converter.LocalDateAttributeConverter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by srikant singh on 30/30/16.
 * Purpose of the class: Model to deal with Web Crawler Information.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Entity
@Table(name = "web_crawler_info")
public class WebCrawlerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "website_url", nullable = false, unique = false)
    private String websiteUrl;

    @Basic
    @Column(name = "requester_email", nullable = false, unique = false)
    private String requesterEmail;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "requested_on", nullable = false)
    private LocalDate requestedOn;

    @Basic
    @Column(name = "num_of_domain_ascii_links", nullable = true)
    private int numberOfDomainAsciiLink;

    @Basic
    @Column(name = "num_of_domain_ascii_utf_links", nullable = true)
    private int numberOfDomainAsciiUtfLink;

    @Basic
    @Column(name = "num_of_domain_links", nullable = true)
    private int numberOfDomainLink;

    @Basic
    @Column(name = "num_of_ext_ascii_links", nullable = true)
    private int numberOfExtAsciiLink;

    @Basic
    @Column(name = "num_of_ext_links", nullable = true)
    private int numberOfExtLink;

    @Basic
    @Column(name = "num_of_inactive_ascii_links", nullable = true)
    private int inactiveAsciiLinkCount;

    @Basic
    @Column(name = "num_of_inactive_links", nullable = true)
    private int inactiveLinkCount;

    @Basic
    @Column(name = "ua_compliance_index", nullable = true)
    private short uaComplianceIndex;

    @Basic
    @Column(name = "ascii_email_count", nullable = true)
    private int asciiEmailCount;

    @Basic
    @Column(name = "email_count", nullable = true)
    private int emailCount;

    @Basic
    @Column(name = "email_fields_count", nullable = true)
    private int emailFieldsCount;

    @Basic
    @Column(name = "time", nullable = true)
    private long processingTime;

    @Basic
    @Column(name = "crawled_urls", columnDefinition = "LONGTEXT", nullable = true)
    private String crawledUrls;


    private WebCrawlerInfo() {
    }

    public WebCrawlerInfo(String websiteUrl, String requesterEmail, LocalDate requestedOn,
                          int numberOfDomainAsciiLink, int numberOfDomainLink, int numberOfDomainAsciiUtfLink, int numberOfExtAsciiLink,
                          int numberOfExtLink, int inactiveAsciiLinkCount, int inactiveLinkCount,
                          short uaComplianceIndex, int asciiEmailCount, int emailCount,
                          int emailFieldsCount, long processingTime, String crawledUrls) {
        this.websiteUrl = websiteUrl;

        this.requesterEmail = requesterEmail;
        this.requestedOn = requestedOn;

        this.numberOfDomainAsciiLink = numberOfDomainAsciiLink;
        this.numberOfDomainAsciiUtfLink = numberOfDomainAsciiUtfLink;
        this.numberOfDomainLink = numberOfDomainLink;
        this.numberOfExtAsciiLink = numberOfExtAsciiLink;
        this.numberOfExtLink = numberOfExtLink;
        this.inactiveLinkCount = inactiveLinkCount;
        this.inactiveAsciiLinkCount = inactiveAsciiLinkCount;

        // Email fields
        this.asciiEmailCount = asciiEmailCount;
        this.emailCount = emailCount;
        this.emailFieldsCount = emailFieldsCount;

        this.uaComplianceIndex = uaComplianceIndex;
        this.processingTime = processingTime;
        this.crawledUrls = crawledUrls;

    }

    public WebCrawlerInfo id(long id) {
        this.id = id;
        return this;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public Long getId() {
        return id;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public LocalDate getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(LocalDate requestedOn) {
        this.requestedOn = requestedOn;
    }

    public short getUaComplianceIndex() {
        return uaComplianceIndex;
    }

    public void setUaComplianceIndex(short uaComplianceIndex) {
        this.uaComplianceIndex = uaComplianceIndex;
    }

    public int getNumberOfDomainAsciiLink() {
        return numberOfDomainAsciiLink;
    }

    public void setNumberOfDomainAsciiLink(int numberOfDomainAsciiLink) {
        this.numberOfDomainAsciiLink = numberOfDomainAsciiLink;
    }

    public int getInactiveLinkCount() {
        return inactiveLinkCount;
    }

    public void setInactiveLinkCount(int inactiveLinkCount) {
        this.inactiveLinkCount = inactiveLinkCount;
    }

    public int getAsciiEmailCount() {
        return asciiEmailCount;
    }

    public void setAsciiEmailCount(int asciiEmailCount) {
        this.asciiEmailCount = asciiEmailCount;
    }

    public int getEmailFieldsCount() {
        return emailFieldsCount;
    }

    public void setEmailFieldsCount(int emailFieldsCount) {
        this.emailFieldsCount = emailFieldsCount;
    }

    public int getNumberOfDomainLink() {
        return numberOfDomainLink;
    }

    public void setNumberOfDomainLink(int numberOfDomainLink) {
        this.numberOfDomainLink = numberOfDomainLink;
    }

    public int getNumberOfExtAsciiLink() {
        return numberOfExtAsciiLink;
    }

    public void setNumberOfExtAsciiLink(int numberOfExtAsciiLink) {
        this.numberOfExtAsciiLink = numberOfExtAsciiLink;
    }

    public int getNumberOfExtLink() {
        return numberOfExtLink;
    }

    public void setNumberOfExtLink(int numberOfExtLink) {
        this.numberOfExtLink = numberOfExtLink;
    }

    public int getInactiveAsciiLinkCount() {
        return inactiveAsciiLinkCount;
    }

    public void setInactiveAsciiLinkCount(int inactiveAsciiLinkCount) {
        this.inactiveAsciiLinkCount = inactiveAsciiLinkCount;
    }

    public String getCrawledUrls() {
        return crawledUrls;
    }

    public int getNumberOfDomainAsciiUtfLink() {
        return numberOfDomainAsciiUtfLink;
    }

    public void setNumberOfDomainAsciiUtfLink(int numberOfDomainAsciiUtfLink) {
        this.numberOfDomainAsciiUtfLink = numberOfDomainAsciiUtfLink;
    }
}
