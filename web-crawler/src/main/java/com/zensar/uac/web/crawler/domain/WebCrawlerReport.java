package com.zensar.uac.web.crawler.domain;

import com.zensar.uac.web.crawler.complexity.calculation.strategy.ComplexityCalculationStrategy;

import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Sagar Balai on 08/30/16.
 * Purpose of the class: Domain object which which will contain basic information for crawling report.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class WebCrawlerReport {

    // Report UA impact meta data.
    private int domainAsciiLinkCount;
    private int domainAsciiUtfLinkCount;
    private int domainLinkCount;
    private int inactiveAsciiLinkCount;
    private int inactiveLinkCount;

    // email info
    private int asciiEmailTextCount;
    private int emailCount;
    private int formFieldsCount;

    // UA compliance  information, aggregate and table with all compliance.
    private int aggregateUAComplianceIndex;
    private int domainLinkCompliance;
    private int htmlUnicodeCompliance;
    private int formElementsCompliance;
    private int inactiveLinkCompliance;
    private int emailTextCompliance;

    //Link compliance criteria
    private int linkComplianceIndex;

    //email compliance criteria
    private int emailComplianceIndex;

    // Information data
    private String crawlingURL;
    private int extAsciiLinkCount;
    private int extLinkCount;
    private LocalDate crawledDate;
    private String emailID;
    private long processingTime;
    private Set<String> domainsLinkSet;

    public WebCrawlerReport(String url, int domainAsciiLinkCount, int domainAsciiUtfLinkCount, int domainLinkCount, int extAsciiLinkCount, int extLinkCount, int inactiveAsciiLinkCount, int inactiveLinkCount, int asciiEmailTextCount, int emailCount, int formFieldsCount, Set<String> domainsLinkSet) {
        this.domainAsciiLinkCount = domainAsciiLinkCount;
        this.domainAsciiUtfLinkCount = domainAsciiUtfLinkCount;
        this.domainLinkCount = domainLinkCount;
        this.inactiveAsciiLinkCount = inactiveAsciiLinkCount;
        this.inactiveLinkCount = inactiveLinkCount;

        this.asciiEmailTextCount = asciiEmailTextCount;
        this.emailCount = emailCount;
        this.formFieldsCount = formFieldsCount;

        this.crawlingURL = url;
        this.extAsciiLinkCount = extAsciiLinkCount;
        this.extLinkCount = extLinkCount;
        this.domainsLinkSet = domainsLinkSet;
    }

    public void calculateComplexity(ComplexityCalculationStrategy strategy) {
        this.aggregateUAComplianceIndex = strategy.calculateAggregateCompliance(this);
        this.domainLinkCompliance = strategy.calculateDomainLinkCompliance(this.domainAsciiLinkCount, this.domainLinkCount);
        this.inactiveLinkCompliance = strategy.calculateInactiveLinkCompliance(this.inactiveLinkCount);
        this.emailTextCompliance = strategy.calculateEmailTextCompliance(this.asciiEmailTextCount, this.emailCount);
        this.htmlUnicodeCompliance = strategy.calculateDomainUtfLinkCompliance(this.domainAsciiLinkCount, this.getDomainAsciiUtfLinkCount());
        this.linkComplianceIndex = strategy.calculateLinkCompliance(this);
        this.emailComplianceIndex = strategy.calculateEmailCompliance(this);
        this.formElementsCompliance = strategy.formElementsCompliance(this.domainLinkCount, this.formFieldsCount);

    }

    public int getAggregateUAComplianceIndex() {
        return aggregateUAComplianceIndex;
    }

    public int getFormElementsCompliance() {
        return formElementsCompliance;
    }

    public void setFormElementsCompliance(int formElementsCompliance) {
        this.formElementsCompliance = formElementsCompliance;
    }

    public String getCrawlingURL() {
        return crawlingURL;
    }

    public int getInactiveLinkCount() {
        return inactiveLinkCount;
    }

    public int getAsciiEmailTextCount() {
        return asciiEmailTextCount;
    }

    public void setAsciiEmailTextCount(int asciiEmailTextCount) {
        this.asciiEmailTextCount = asciiEmailTextCount;
    }

    public int getFormFieldsCount() {
        return formFieldsCount;
    }

    public void setFormFieldsCount(int formFieldsCount) {
        this.formFieldsCount = formFieldsCount;
    }

    public int getDomainAsciiLinkCount() {
        return domainAsciiLinkCount;
    }

    public int getDomainLinkCount() {
        return domainLinkCount;
    }

    public int getExtAsciiLinkCount() {
        return extAsciiLinkCount;
    }

    public int getExtLinkCount() {
        return extLinkCount;
    }

    public int getDomainLinkCompliance() {
        return domainLinkCompliance;
    }

    public void setDomainLinkCompliance(int domainLinkCompliance) {
        this.domainLinkCompliance = domainLinkCompliance;
    }

    public int getInactiveLinkCompliance() {
        return inactiveLinkCompliance;
    }

    public void setInactiveLinkCompliance(int inactiveLinkCompliance) {
        this.inactiveLinkCompliance = inactiveLinkCompliance;
    }

    public int getEmailTextCompliance() {
        return emailTextCompliance;
    }

    public void setEmailTextCompliance(int emailTextCompliance) {
        this.emailTextCompliance = emailTextCompliance;
    }


    public LocalDate getCrawledDate() {
        return crawledDate;
    }

    public void setCrawledDate(LocalDate crawledDate) {
        this.crawledDate = crawledDate;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
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

    public void setAggregateUAComplianceIndex(int aggregateUAComplianceIndex) {
        this.aggregateUAComplianceIndex = aggregateUAComplianceIndex;
    }

    public void setCrawlingURL(String crawlingURL) {
        this.crawlingURL = crawlingURL;
    }

    public void setExtAsciiLinkCount(int extAsciiLinkCount) {
        this.extAsciiLinkCount = extAsciiLinkCount;
    }

    public void setExtLinkCount(int extLinkCount) {
        this.extLinkCount = extLinkCount;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }

    public int getLinkComplianceIndex() {
        return linkComplianceIndex;
    }

    public void setLinkComplianceIndex(int linkComplianceIndex) {
        this.linkComplianceIndex = linkComplianceIndex;
    }

    public int getEmailComplianceIndex() {
        return emailComplianceIndex;
    }

    public void setEmailComplianceIndex(int emailComplianceIndex) {
        this.emailComplianceIndex = emailComplianceIndex;
    }

    public Set<String> getDomainsLinkSet() {
        return domainsLinkSet;
    }

    public void setDomainsLinkSet(Set<String> domainsLinkSet) {
        this.domainsLinkSet = domainsLinkSet;
    }

    public int getDomainAsciiUtfLinkCount() {
        return domainAsciiUtfLinkCount;
    }

    public void setDomainAsciiUtfLinkCount(int domainAsciiUtfLinkCount) {
        this.domainAsciiUtfLinkCount = domainAsciiUtfLinkCount;
    }

    public int getHtmlUnicodeCompliance() {
        return htmlUnicodeCompliance;
    }

    public void setHtmlUnicodeCompliance(int htmlUnicodeCompliance) {
        this.htmlUnicodeCompliance = htmlUnicodeCompliance;
    }
}
