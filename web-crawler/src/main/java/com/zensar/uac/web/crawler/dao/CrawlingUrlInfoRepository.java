package com.zensar.uac.web.crawler.dao;

import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by srikant.singh on 10/20/2016.
 * Purpose of the class: This class deals with persisting Crawling URL info.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface CrawlingUrlInfoRepository extends CrudRepository<CrawlingURLInfo, Long> {

    /**
     * Deletes the crawling url information of the given crawling url link from database.
     *
     * @param crawlingUrl   the crawling url link
     * @return              the number of rows deleted
     */
    @Transactional
    Long deleteByCrawlingUrl(String crawlingUrl);


    /**
     * Saves the crawling url information in database.
     *
     * @param crawlingURLInfo   the information of crawling url link
     * @return                  the information of crawling url link
     */
    @Transactional
    CrawlingURLInfo save(CrawlingURLInfo crawlingURLInfo);


    /**
     * Returns the number of rows in database that match the given crawling url.
     *
     * @param crawlingUrl   the crawling url link
     * @return              the number of rows in database that match the given crawling url
     */
    @Transactional
    int countByCrawlingUrl(String crawlingUrl);


    /**
     * Returns the object containing information related to crawling url
     * that matches the given crawling url link.
     *
     * @param crawlingUrl   the crawling url link
     * @return              the the object containing information related to crawling url
     */
    @Transactional
    CrawlingURLInfo findByCrawlingUrl(String crawlingUrl);


    /**
     * Returns the number of rows in database that match the given crawling url and status.
     *
     * @param crawlingUrl   the crawling url link
     * @param status        the status of crawling url
     * @return              the number of rows in database that match the given crawling url and status
     */
    @Transactional
    int countByCrawlingUrlAndStatus(String crawlingUrl, CrawlingURLInfo.Status status);


}
