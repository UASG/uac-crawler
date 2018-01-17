package com.zensar.uac.web.crawler.services.impl;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.services.UACService;
import com.zensar.uac.web.crawler.services.UACWebCrawler;
import com.zensar.uac.web.crawler.services.reports.strategy.CrawlerReportStrategy;
import com.zensar.uac.web.crawler.services.reports.strategy.factory.CrawlerReportStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: UACServiceImpl is responsible to create crawling report for new URL or already processed URL's.
 * This is basic interface and exposed to external user and will act as starting point for this web crawler application.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UACServiceImpl implements UACService {

    @Autowired
    private UACWebCrawler webCrawler;
    @Autowired
    private CrawlerReportStrategyFactory factory;

    public WebCrawlerReport crawl(String url, String emailID) {
        return webCrawler.crawl(url, emailID);
    }

    public List<WebCrawlerReport> generateReport(String inputURL) {
        String strategyKey = "S";
        CrawlerReportStrategy strategy = factory.getStrategy(inputURL, strategyKey);
        return strategy.generate();
    }

    public List<WebCrawlerReport> generateReport() {
        String strategyKey = "C";
        CrawlerReportStrategy strategy = factory.getStrategy(null, strategyKey);
        return strategy.generate();
    }

}
