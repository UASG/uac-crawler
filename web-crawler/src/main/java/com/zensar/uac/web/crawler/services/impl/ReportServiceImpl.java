package com.zensar.uac.web.crawler.services.impl;

import com.zensar.uac.web.crawler.dao.CrawlingUrlInfoRepository;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import com.zensar.uac.web.crawler.services.ReportService;
import com.zensar.uac.web.crawler.services.UACService;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by srikant.singh on 09/01/2016.
 */

/**
 * Created by srikant.singh on 09/01/2016.
 * Purpose of the class: ReportServiceImpl is responsible to create WebCrawlerReport report for for givin URL
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UACService uacService;

    private CrawlingUrlInfoRepository crawlingUrlInfoRepository;

    /*Code changes for testing*/

    public ReportServiceImpl(@Inject CrawlingUrlInfoRepository crawlingUrlInfoRepository) {
        this.crawlingUrlInfoRepository = crawlingUrlInfoRepository;
    }


    public WebCrawlerReport generateReport(String url, String emailAddress) {
        return uacService.crawl(url, emailAddress);
    }

    public int checkCrawlingUrl(String url) {
        int count = 0;
        count = crawlingUrlInfoRepository.countByCrawlingUrlAndStatus(url, CrawlingURLInfo.Status.PROCESSING);
        count += crawlingUrlInfoRepository.countByCrawlingUrlAndStatus(url, CrawlingURLInfo.Status.QUEUE);
        return count;
    }
}
