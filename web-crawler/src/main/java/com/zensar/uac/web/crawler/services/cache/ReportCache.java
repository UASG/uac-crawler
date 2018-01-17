package com.zensar.uac.web.crawler.services.cache;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Basic interface for caching
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface ReportCache {

    /**
     * Stores the given information of a website in cache.
     *
     * @param report    the object that has to be saved in cache
     */
    void store(WebCrawlerReport report);


    /**
     * Retrieves the information of given url from cache.
     *
     * @param url   the url of the website whose data has to be retrieved from the cache
     * @return      the object containing all the information related to the given url
     */
    WebCrawlerReport retrieve(String url);
}
