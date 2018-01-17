package com.zensar.uac.web.crawler.services.impl;

import com.zensar.uac.web.crawler.CrawledTask;
import com.zensar.uac.web.crawler.complexity.calculation.strategy.factory.ComplexityCalStrategyFactory;
import com.zensar.uac.web.crawler.core.database.EntityStoreManager;
import com.zensar.uac.web.crawler.core.database.WebCrawlerData;
import com.zensar.uac.web.crawler.dao.CrawledUrlRepository;
import com.zensar.uac.web.crawler.dao.CrawlingUrlInfoRepository;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.html.HTMLCrawler;
import com.zensar.uac.web.crawler.model.CrawledUrl;
import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import com.zensar.uac.web.crawler.services.UACWebCrawler;
import com.zensar.uac.web.crawler.services.cache.ReportCache;
import com.zensar.uac.web.crawler.util.ThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: Crawler is responsible for crawling the given URL and return crawler report which inclues all criteria which impacts complexity of URL like
 * 1. All URL  in page with ASCII code
 * 2  1. All URL  in page with ASCII code with utf charset
 * 3. Static links
 * 4. Double byte characters
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component
@Scope("prototype")
public class UACWebCrawlerImpl implements UACWebCrawler {
    @Autowired
    private HTMLCrawler htmlCrawler;
    @Autowired
    private EntityStoreManager entityStoreManager;
    @Autowired
    private ReportCache reportCache;
    @Autowired
    private ComplexityCalStrategyFactory calFactory;
    @Autowired
    private CrawlingUrlInfoRepository crawlingUrlInfoRepository;

    @Autowired
    private CrawledUrlRepository crawledUrlRepository;

    public WebCrawlerReport crawl(String inputURL, String emailID) {
        WebCrawlerReport report = null;
        WebCrawlerReport reportFromCache = findInCache(inputURL);
        if (reportFromCache != null) {
            report = reportFromCache;
            report.calculateComplexity(calFactory.getBracketStrategy());
        } else {
            WebCrawlerReport reportFromDatabase = findInDatabase(inputURL);

            if (reportFromDatabase != null) {
                report = reportFromDatabase;
                report.calculateComplexity(calFactory.getBracketStrategy());
                saveInCache(report);
            } else {
                CrawlingURLInfo crawlingURLInfo = new CrawlingURLInfo(inputURL, CrawlingURLInfo.Status.QUEUE, LocalDate.now());
                crawlingUrlInfoRepository.save(crawlingURLInfo);
                CrawledTask task = new CrawledTask(inputURL, htmlCrawler, emailID, entityStoreManager, calFactory, crawlingUrlInfoRepository, crawledUrlRepository);
                ThreadPool.getThreadPool().submit(task);
                report = new WebCrawlerReport("", -10, 0, 0, 0, 0, 0, 0, 0, 0, 0, new HashSet<>());
            }
        }

        if (emailID != null && !emailID.isEmpty()) {
            // send email to 'emailID'
        }
        report.setCrawlingURL(inputURL);
        report.setEmailID(emailID);

        return report;
    }

    /*
    This method checks if the report for given url is present in cache, if its available, its retrieved from cache.
    The argument inputURL is the url of website whose report has to be retrieved from cache.
    It returns the object containing all the information related to the given url.
    */
    private WebCrawlerReport findInCache(String inputURL) {
        return reportCache.retrieve(inputURL);
    }

    /*
    This method checks if the report for given url is present in database, if its available, its retrieved from database.
    The argument inputURL is the url of website whose report has to be retrieved from database.
    It returns the object containing all the information related to the given url.
    */
    private WebCrawlerReport findInDatabase(String inputURL) {
        WebCrawlerData data = entityStoreManager.retrieve(inputURL);
        CrawlingURLInfo crawlingURLInfo = crawlingUrlInfoRepository.findByCrawlingUrl(inputURL);
        List<CrawledUrl> crawledUrlList = crawledUrlRepository.findBycrawlingURLInfo(crawlingURLInfo);
        Set<String> crawledUrl = new LinkedHashSet<>();
        for (CrawledUrl url : crawledUrlList) {
            crawledUrl.add(url.getCrawledUrl());
        }
        if (data == null) {
            return null;
        } else {
            WebCrawlerReport webCrawlerReport = data.getCrawlingReport();
            webCrawlerReport.setDomainsLinkSet(crawledUrl);
            return webCrawlerReport;
        }
    }

    /*
    This method saves the given information of a website in cache.
    The argument report is the object that has to be saved in cache.
    */
    private void saveInCache(WebCrawlerReport report) {
        reportCache.store(report);
    }
}
