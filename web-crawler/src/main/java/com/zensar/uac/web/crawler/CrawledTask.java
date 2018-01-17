package com.zensar.uac.web.crawler;

import com.zensar.uac.web.crawler.complexity.calculation.strategy.factory.ComplexityCalStrategyFactory;
import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import com.zensar.uac.web.crawler.core.database.EntityStoreManager;
import com.zensar.uac.web.crawler.core.database.WebCrawlerData;
import com.zensar.uac.web.crawler.dao.CrawledUrlRepository;
import com.zensar.uac.web.crawler.dao.CrawlingUrlInfoRepository;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.html.HTMLCrawler;
import com.zensar.uac.web.crawler.model.CrawledUrl;
import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import com.zensar.uac.web.crawler.services.impl.EmailServiceImpl;
import com.zensar.uac.web.crawler.util.PDFGenerator;
import com.zensar.uac.web.crawler.util.UrlUtil;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: Task for every request for crawling and generate report.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class CrawledTask implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(CrawledTask.class.getName());

    private String inputUrl;

    private HTMLCrawler htmlCrawler;

    private String requestorEmailId;

    private EntityStoreManager entityStoreManager;

    private ComplexityCalStrategyFactory calFactory;

    private CrawlingUrlInfoRepository crawlingUrlInfoRepository;

    private CrawledUrlRepository crawledUrlRepository;

    public CrawledTask() {
    }

    public CrawledTask(String inputUrl, HTMLCrawler htmlCrawler, String requestorEmailId, EntityStoreManager entityStoreManager, ComplexityCalStrategyFactory calFactory, CrawlingUrlInfoRepository crawlingUrlInfoRepository, CrawledUrlRepository crawledUrlRepository) {
        this.inputUrl = inputUrl;
        this.htmlCrawler = htmlCrawler;
        this.requestorEmailId = requestorEmailId;
        this.entityStoreManager = entityStoreManager;
        this.calFactory = calFactory;
        this.crawlingUrlInfoRepository = crawlingUrlInfoRepository;
        this.crawledUrlRepository = crawledUrlRepository;

    }

    public void run() {

        CrawlingURLInfo crawlingURLInfo = crawlingUrlInfoRepository.findByCrawlingUrl(inputUrl);
        if(crawlingURLInfo == null) {
            crawlingURLInfo =  new CrawlingURLInfo(inputUrl, CrawlingURLInfo.Status.PROCESSING, LocalDate.now());
        } else {
            crawlingURLInfo.setStatus(CrawlingURLInfo.Status.PROCESSING);
        }
        crawlingUrlInfoRepository.save(crawlingURLInfo);
        WebCrawlerReport webCrawlerReport = htmlCrawler.crawl(inputUrl);
        reportWithUrls(webCrawlerReport);
        webCrawlerReport.calculateComplexity(calFactory.getBracketStrategy());

        try {
            webCrawlerReport.setEmailID(requestorEmailId);
            PDFGenerator.generate(webCrawlerReport);
            String fileName = UrlUtil.getFilenameFromUrl(webCrawlerReport.getCrawlingURL());
            fileName += ".pdf";

            FileSystem fileSystems = FileSystems.getDefault();
            Path filePath = fileSystems.getPath("PDF/" + fileName).normalize();
            File file = filePath.toFile();
            Set<String> dummySet = new LinkedHashSet<>();
            dummySet.add(webCrawlerReport.getCrawlingURL());
            webCrawlerReport.setDomainsLinkSet(dummySet);
            saveInDatabase(requestorEmailId, webCrawlerReport);
            EmailServiceImpl.sendEmail(file, requestorEmailId, "Hi Please find the attached web Crawler analyser report", "Web Crawler Analyser Report");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
        } finally {
            crawlingURLInfo.setStatus(CrawlingURLInfo.Status.COMPLETED);
            crawlingUrlInfoRepository.save(crawlingURLInfo);
        }

    }


    /*
    This method is used to save web crawler report in database.
    */
    private void saveInDatabase(String emailID, WebCrawlerReport report) {
        WebCrawlerData data = new WebCrawlerData(emailID, report);
        entityStoreManager.store(data);
    }

    /*
    This method is used to get the list of urls that have been crawled for the given website.
    */
    private void reportWithUrls(WebCrawlerReport webCrawlerReport) {
        List<CrawledUrl> crawledUrls = crawledUrlRepository.findBycrawlingURLInfo(crawlingUrlInfoRepository.findByCrawlingUrl(webCrawlerReport.getCrawlingURL()));
        Set<String> urls = new LinkedHashSet<>();
        for (CrawledUrl crawledUrl : crawledUrls) {
            urls.add(crawledUrl.getCrawledUrl());
        }
        webCrawlerReport.setDomainsLinkSet(urls);
    }
}
