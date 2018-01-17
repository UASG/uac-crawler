package com.zensar.uac.web.crawler.services.reports.strategy.impl;

import com.zensar.uac.web.crawler.core.database.EntityStoreManager;
import com.zensar.uac.web.crawler.core.database.WebCrawlerData;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.services.reports.strategy.CrawlerReportStrategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: Strategy used to generate crawling report and in this
 * strategy all URL which is present in DB will be considered and list
 * of report will be created for all URL.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component(value = "allURLStrategy")
public class AllURLStrategy implements CrawlerReportStrategy {
    @Autowired
    private EntityStoreManager entityStoreManager;

    public List<WebCrawlerReport> generate() {
        List<WebCrawlerReport> report = new ArrayList<>();

        List<WebCrawlerData> dataList = entityStoreManager.retrieveAllEntity();
        for (WebCrawlerData data : dataList) {
            report.add(data.getCrawlingReport());
        }

        return report;
    }

}
