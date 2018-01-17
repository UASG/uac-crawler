package com.zensar.uac.web.crawler.dao;

import com.zensar.uac.web.crawler.model.WebCrawlerInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kiran.govind on 08/30/16.
 * Purpose of the class: Dao Repository for the model WebCrawlerInfo
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface WebCrawlerInfoRepository extends CrudRepository<WebCrawlerInfo, Long> {

    /**
     * Returns the information of website where website url matches the give url.
     *
     * @param websiteUrl    the url of website
     * @return              the UA compliance index of website
     */
    WebCrawlerInfo findUacIndexByWebsiteUrl(String websiteUrl);


    /**
     * Returns the information of website where requester email matches the given email id.
     *
     * @param requesterEmail    the requester email id
     * @return                  the UA compliance index of website
     */
    WebCrawlerInfo findUacIndexByRequesterEmail(String requesterEmail);
}
