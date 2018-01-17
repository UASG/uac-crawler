package com.zensar.uac.web.crawler.core.database;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;

import java.time.LocalDate;

/**
 * Created by Sagar on 10/24/2016.
 * Purpose of the class: Wrapper of WebCrawlerReport with additional information as
 * client emailID and date on which request came from client.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class WebCrawlerData {
    private String emailID;
    private WebCrawlerReport crawlingReport;
    private LocalDate crawledDate;

    public WebCrawlerData(String mailID, WebCrawlerReport report) {
        this.emailID = mailID;
        this.crawlingReport = report;
        this.crawledDate = LocalDate.now();
    }

    public LocalDate getCrawledDate() {
        return crawledDate;
    }

    public String getEmailID() {
        return emailID;
    }

    public WebCrawlerReport getCrawlingReport() {
        return crawlingReport;
    }

}
