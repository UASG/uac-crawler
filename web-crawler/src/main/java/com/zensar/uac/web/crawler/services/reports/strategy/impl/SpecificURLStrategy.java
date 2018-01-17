package com.zensar.uac.web.crawler.services.reports.strategy.impl;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.services.UACWebCrawler;
import com.zensar.uac.web.crawler.services.impl.UACWebCrawlerImpl;
import com.zensar.uac.web.crawler.services.reports.strategy.CrawlerReportStrategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: Strategy used to generate crawling report and in this
 * strategy specific URL. If specific URL is present in Cache it will
 * pick it from cache and return result else Will check in database and
 * if present retrieve crawler report from database else It will freshly
 * calculate complexity and create crawler report.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component(value = "specificURLStrategy")
public class SpecificURLStrategy implements CrawlerReportStrategy {
    private String inputURL;

    public SpecificURLStrategy() {
    }

    public SpecificURLStrategy(String inputURL) {
        this.inputURL = inputURL;
    }

    public List<WebCrawlerReport> generate() {
        List<WebCrawlerReport> reportList = new ArrayList<>();
        UACWebCrawler webCrawler = new UACWebCrawlerImpl();
        reportList.add(webCrawler.crawl(inputURL, null));
        return reportList;
    }

    public void setInputURL(String inputURL) {
        this.inputURL = inputURL;
    }

}
