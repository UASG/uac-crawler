package com.zensar.uac.web.crawler.services.reports.strategy.impl;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.services.reports.strategy.CrawlerReportStrategy;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: It is crawling report generation strategy, it will return
 * crawler report only for processed URL and stored in database. It will
 * read number from properties file and return only those number of
 * crawler report.
 * If database contains less record than asked it will return only
 * available number of records from database.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component(value = "topComplexityCountStrategy")
public class TopComplexityCountStrategy implements CrawlerReportStrategy {
    public List<WebCrawlerReport> generate() {
        return Collections.emptyList();
    }

}
