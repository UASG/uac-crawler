package com.zensar.uac.web.crawler.services;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;

import java.util.List;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: UACService is responsible to create crawling report for new URL or already processed URL's.
 * This is basic interface and exposed to external user and will act as starting point for this web crawler application.
 * <p>
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface UACService {

    /**
     * Generates report for given website and sends the report as attachment to the given email address.
     * If report is present in cache, its retrieved from cache else its retrieved from database.
     * If there is no information available in database related to the given website,
     * a new crawl process is started for the given url.
     *
     * @param url       the url of website whose report has to be generated
     * @param emailID   the email address where report has to be sent as attachment
     * @return          the object containing all the information related to the given url
     */
    WebCrawlerReport crawl(String url, String emailID);

    List<WebCrawlerReport> generateReport(String inputURL);
}
