package com.zensar.uac.web.crawler.services.cache.impl;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.services.cache.ReportCache;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: class for maintaining for caching
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component
public class ReportCacheImpl implements ReportCache {
    private Map<String, WebCrawlerReport> reportCacheMap = new HashMap<>();

    public void store(WebCrawlerReport report) {
        reportCacheMap.put(report.getCrawlingURL(), report);
    }

    public WebCrawlerReport retrieve(String url) {
        return reportCacheMap.get(url);
    }

}
