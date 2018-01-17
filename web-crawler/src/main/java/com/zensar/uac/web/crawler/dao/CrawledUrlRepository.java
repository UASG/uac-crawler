package com.zensar.uac.web.crawler.dao;

import com.zensar.uac.web.crawler.model.CrawledUrl;
import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by srikant.singh on 10/24/2016.
 * Purpose of the class: This class deals with persisting Crawled internal URLs.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface CrawledUrlRepository extends CrudRepository<CrawledUrl, Long> {

    /**
     * Saves the crawled url information in database.
     *
     * @param crawledUrl    the information of crawled url link
     * @return              the information of crawled url link
     */
    @Transactional
    CrawledUrl save(CrawledUrl crawledUrl);


    /**
     * Returns the list of crawled url links where crawledUrl matches.
     *
     * @param crawledUrl    the crawled url link
     * @return              the list of crawled url links
     */
    @Transactional
    List<CrawledUrl> findByCrawledUrl(String crawledUrl);


    /**
     * Returns the list of crawled url links where crawlingURLInfo matches.
     *
     * @param crawlingURLInfo   the object containing information related to the crawling url
     * @return                  the list of crawled url links
     */
    @Transactional
    List<CrawledUrl> findBycrawlingURLInfo(CrawlingURLInfo crawlingURLInfo);

}
