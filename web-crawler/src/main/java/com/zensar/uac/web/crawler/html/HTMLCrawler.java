package com.zensar.uac.web.crawler.html;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Sagar Balai on 08/30/16.
 * Purpose of the class: It will crawl 'inputURL' and give WebCrawlerReport object
 * layer.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

@Component
@Scope("prototype")
public class HTMLCrawler {

    @Autowired
    private URLCrawler urlCrawler;

    /**
     * Returns the report containing detailed information of the crawled website.
     *
     * @param   inputURL    the url that has to be crawled
     * @return              the report containing detailed information
     */
    public WebCrawlerReport crawl(String inputURL) {
        long startTime = System.currentTimeMillis();
        WebCrawlerReport report = urlCrawler.crawl(inputURL);
        long endTime = System.currentTimeMillis();
        long crawlReportTime = endTime - startTime;
        report.setProcessingTime(crawlReportTime);
        return report;
    }
}
