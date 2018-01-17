package com.zensar.uac.web.crawler.core.database;

import java.util.List;

/**
 * Created by Sagar on 10/24/2016.
 * Purpose of the class: persist WebCrawlerData
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public interface EntityStoreManager {

    /**
     * Stores the given information of a website in database.
     *
     * @param data  the object containing website information that has to be saved in database
     */
    void store(WebCrawlerData data);


    /**
     * Retrieves the information of given url from database.
     *
     * @param url   the url of the website whose data has to be retrieved from the database
     * @return      the object containing all the information related to the given url
     */
    WebCrawlerData retrieve(String url);


    /**
     * Returns the list of all websites from database along with their information.
     *
     * @return  the list of all websites from database
     */
    List<WebCrawlerData> retrieveAllEntity();
}
