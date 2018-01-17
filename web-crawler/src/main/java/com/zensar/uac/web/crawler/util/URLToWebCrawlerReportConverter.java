package com.zensar.uac.web.crawler.util;

import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.pojo.URLCrawlerReport;

/**
 * Created by Sagar Balai on 04-10-2016.
 */

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: Use to convert from  URLCrawlerReport to WebCrawlerReport object.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public class URLToWebCrawlerReportConverter {

    //Added a private constructor as this class contains only static methods so no need to instantiate this class.
    private URLToWebCrawlerReportConverter() {
    }

    /**
     * Create WebCrawlerReport by URLCrawlerReport and web site url.
     *
     * @param inputUrl
     * @param urlReport
     * @return
     */
    public static WebCrawlerReport convertURLReportToWebCrawlerReport(String inputUrl, URLCrawlerReport urlReport) {
        return new WebCrawlerReport(inputUrl,
                urlReport.getDomainAsciiLinkCount(),
                urlReport.getDomainAsciiUtfLinkCount(),
                urlReport.getDomainLinkCount(),
                urlReport.getExtAsciiLinkCount(), urlReport.getExtLinkCounnt(),
                urlReport.getInactiveAsciiLinkCount(), urlReport.getInactiveLinkCount(),
                urlReport.getAsciiEmails().size(), urlReport.getEmails().size(),
                urlReport.getFormFieldCount(), urlReport.getDomainLinks());
    }


}
