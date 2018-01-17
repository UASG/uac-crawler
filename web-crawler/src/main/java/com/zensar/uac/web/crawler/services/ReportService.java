package com.zensar.uac.web.crawler.services;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: ReportService is responsible to create WebCrawlerReport report for for givin URL
 * This is basic interface
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface ReportService {

    /**
     * Generates report for given website and sends the report as attachment to the given email address.
     * If report is present in cache, its retrieved from cache else its retrieved from database.
     * If there is no information available in database related to the given website,
     * a new crawl process is started for the given url.
     *
     * @param url           the url of website whose report has to be generated
     * @param emailAddress  the email address where report has to be sent as attachment
     * @return              the object containing all the information related to the given url
     */
    WebCrawlerReport generateReport(String url, String emailAddress);


    /**
     * Checks whether the given url is present in 'crawling_url_info' table.
     *
     * @param url   the url of website whose crawling status has to be checked
     * @return      returns 1 if website status is COMPLETED, QUEUE, PROCESSING else returns 0.
     */
    int checkCrawlingUrl(String url);
}
