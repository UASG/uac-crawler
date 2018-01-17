package com.zensar.uac.web.crawler.complexity.calculation.strategy;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;

/**
 * Created by Sagar Balai on 10/24/2016.
 * Purpose of the class: Strategy interface to calculate complexity of given site
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface ComplexityCalculationStrategy {

    /**
     * Returns the aggregate compliance of the website. The aggregate compliance is the UA compliance index of the website.
     * The webCrawlerReport argument must contain all the information of the website
     * whose aggregate compliance has to be calculated.
     *
     * @param webCrawlerReport  report of the website whose aggregate compliance has to be calculated
     * @return                  the aggregate compliance (UA compliance index) of the website
     */
    int calculateAggregateCompliance(WebCrawlerReport webCrawlerReport);


    /**
     * Returns the email text compliance of the website.
     *
     * @param asciiEmailCount   number of ASCII email fields in the website
     * @param emailCount        number of email fields in the website
     * @return                  the email text compliance of the website
     */
    int calculateEmailTextCompliance(int asciiEmailCount, int emailCount);


    /**
     * Returns the email field compliance of the website.
     *
     * @param emailFeildCount   number of email fields in the website
     * @return                  the email field compliance of the website
     */
    int calculateEmailFieldCompliance(int emailFeildCount);


    /**
     * Returns the inactive link compliance of the website.
     *
     * @param inactiveLinkCount number of inactive links in the website
     * @return                  the inactive link compliance of the website
     */
    int calculateInactiveLinkCompliance(int inactiveLinkCount);


    /**
     * Returns the domain link compliance of the website.
     *
     * @param domainAsciiLinkCount  number of domain ASCII links in the website
     * @param domainLinkCount       total number of domain links (including ASCII and UTF links) in the website
     * @return                      the domain link compliance of the website
     */
    int calculateDomainLinkCompliance(int domainAsciiLinkCount, int domainLinkCount);


    /**
     * Returns the domain UTF link compliance of the website.
     *
     * @param domainAsciiLinkCount  number of domain ASCII links in the website
     * @param domainUtfLinkCount    number of domain UTF links in the website
     * @return                      the domain UTF link compliance of the website
     */
    int calculateDomainUtfLinkCompliance(int domainAsciiLinkCount, int domainUtfLinkCount);


    /**
     * Returns the link compliance index of the website.
     *
     * @param webCrawlerReport  report of the website whose link compliance has to be calculated
     * @return                  the link compliance index of the website
     */
    int calculateLinkCompliance(WebCrawlerReport webCrawlerReport);


    /**
     * Returns the email compliance index of the website.
     *
     * @param webCrawlerReport  report of the website whose email compliance has to be calculated
     * @return                  the email compliance index of the website
     */
    int calculateEmailCompliance(WebCrawlerReport webCrawlerReport);


    /**
     * Returns the form field compliance of the website.
     *
     * @param domainLinkCount   total number of domain links (including ASCII and UTF links) in the website
     * @param formFieldsCount   number of form fields in the website
     * @return                  the form field compliance of the website
     */
    int formElementsCompliance(int domainLinkCount, int formFieldsCount);
}
