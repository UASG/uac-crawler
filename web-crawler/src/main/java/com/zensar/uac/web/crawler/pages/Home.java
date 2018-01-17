package com.zensar.uac.web.crawler.pages;

import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.services.ReportService;
import com.zensar.uac.web.crawler.util.ASCIIFormatChecker;
import com.zensar.uac.web.crawler.util.UrlUtil;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.IDN;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Page class for Home
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class Home {

    private static final Logger LOGGER = Logger.getLogger(Home.class.getName());

    @Property
    private String url;

    @Property
    private String emailAddress;

    @Inject
    private ReportService reportService;

    @SessionState(create = false)
    private WebCrawlerReport webCrawlerReport;

    @Inject
    private AlertManager alertManager;

    /**
     * Sets the page's activation context before it is rendered.
     *
     * @return
     */
    @OnEvent(EventConstants.ACTIVATE)
    Object onActivate() {
        return true;
    }

    /**
     * Returns the page class where the user should be redirected after success event.
     *
     * @return  the page class where the user should be redirected after success event
     */
    @OnEvent(component = "inputForm", value = EventConstants.SUCCESS)
    Object onSuccess() {
        Document doc = null;
        Connection.Response response = null;
        url = url.trim();

        if (url.contains("xn--")) {
            url = IDN.toUnicode(url);
        }
        if (!ASCIIFormatChecker.check(url)) {
            url = IDN.toASCII(url);
        }
        String url = UrlUtil.getCorrectHttpUrl(this.url);

        try {
            doc = Jsoup.connect(url).get();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
            url = CrawlerConstants.HTTPS + CrawlerConstants.COLON
                    + CrawlerConstants.DOUBLE_BACKSLASH_CHARACTER + this.url;
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e1) {
                LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e1);
                try {
                    response = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                            .timeout(10000)
                            .execute();
                    doc = response.parse();
                } catch (IOException e2) {
                    LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e2);
                    alertManager.error("Your site could not be reached by us, Please contact us for more details.");
                    return Home.class;
                }
            }
        }
        try {
            String[] array = doc.location().split("/");
            String inputUrl = null;
            String uniCodeUrl = this.url;

            if (array.length > 3) {
                inputUrl = array[0] + "/" + array[1] + "/" + array[2] + "/";
            } else {
                inputUrl = doc.location();
            }
            int count = reportService.checkCrawlingUrl(inputUrl);
            if (count == 0) {
                if (inputUrl.contains("xn--")) {
                    webCrawlerReport = reportService.generateReport(uniCodeUrl, emailAddress);
                } else {
                    webCrawlerReport = reportService.generateReport(inputUrl, emailAddress);
                }
            } else {
                alertManager.success("Given URL is under crawling");
                return Home.class;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
            return Home.class;
        }
        return Report.class;
    }

}
