package com.zensar.uac.web.crawler.services.reports.strategy;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;

import java.util.List;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: Strategy used to generate crawling report.s
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public interface CrawlerReportStrategy {
    List<WebCrawlerReport> generate();
}
