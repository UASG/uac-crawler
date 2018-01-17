package com.zensar.uac.web.crawler.html;

import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import com.zensar.uac.web.crawler.dao.CrawledUrlRepository;
import com.zensar.uac.web.crawler.dao.CrawlingUrlInfoRepository;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.html.filter.DomainFilter;
import com.zensar.uac.web.crawler.html.filter.ProcessedUrlRepository;
import com.zensar.uac.web.crawler.model.CrawledUrl;
import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import com.zensar.uac.web.crawler.pojo.EmailCrawlerReport;
import com.zensar.uac.web.crawler.pojo.URLCrawlerReport;
import com.zensar.uac.web.crawler.util.ASCIIFormatChecker;
import com.zensar.uac.web.crawler.util.DomainUtil;
import com.zensar.uac.web.crawler.util.URLToWebCrawlerReportConverter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.net.IDN;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sagar Balai on 08/30/16.
 * Purpose of the class: It will crawl 'inputURL' and give number of URL's which are
 * present on that HTML page as well as all internal pages up to nth
 * layer.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */


@Component
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class URLCrawler {

    private static final Logger LOGGER = Logger.getLogger(URLCrawler.class.getName());

    @Autowired
    private CrawledUrlRepository crawledUrlRepository;
    @Autowired
    private CrawlingUrlInfoRepository crawlingUrlInfoRepository;
    private DomainFilter domainFilter = new DomainFilter();
    private ProcessedUrlRepository processedUrl = new ProcessedUrlRepository();
    private EmailCrawler emailCrawler = new EmailCrawler();
    private FormFieldsCrawler fromFieldCrawler = new FormFieldsCrawler();
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    /**
     * Crawl input web site URL and it will return WebCrawlerReport which has
     * information of all internal links, external links. It will crawl all
     * links which are present on given URL page and all links which are present
     * on internal links pages. This is done for nth level and internally used
     * DFS algorithm to calculate report for give web site URL.
     *
     * @param inputUrl : web site URL for which web crawler report needs to be
     *                 calculated.
     * @return : WebCrawlerReport - report with all impacting measures.
     */
    public WebCrawlerReport crawl(String inputUrl) {
        URLCrawlerReport report = null;
        int inactiveAsciiLinkCount = 0;
        int inactiveLinkCount = 0;
        int domainAsciiLinks = 0;
        int domainAsciUtfLinks = 0;
        int domainLinks = 0;
        int extAsciiLinks = 0;
        int extLinks = 0;
        int formFieldCount = 0;
        Set<String> asciiEmails = new LinkedHashSet<>();
        Set<String> emails = new LinkedHashSet<>();
        Set<String> domainLinksSet = new LinkedHashSet<>();
        processedUrl.getProcessedUrlSet().clear();

        String domain = DomainUtil.getDomain(inputUrl);
        domainFilter.setDomain(domain);
        domainFilter.setLocation(inputUrl);


        CrawlingURLInfo crawlingURLInfo = crawlingUrlInfoRepository.findByCrawlingUrl(inputUrl);

        try {
            queue.put(inputUrl);
            while (!queue.isEmpty() && domainAsciiLinks < 25001) {
                report = getHTMLPageReport(queue.take(), domainLinksSet, crawlingURLInfo);
                if (report != null) {
                    domainAsciiLinks += report
                            .getDomainAsciiLinkCount();
                    domainAsciUtfLinks += report.getDomainAsciiUtfLinkCount();
                    domainLinks += report.getDomainLinkCount();
                    extAsciiLinks += report.getExtAsciiLinkCount();
                    extLinks += report.getExtLinkCounnt();
                    inactiveAsciiLinkCount += report.getInactiveAsciiLinkCount();
                    inactiveLinkCount += report.getInactiveLinkCount();

                    // email info
                    formFieldCount += report.getFormFieldCount();
                    emails.addAll(report.getEmails());
                    asciiEmails.addAll(report.getAsciiEmails());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
        }
        pushData(domainLinksSet, crawlingURLInfo);
        report = new URLCrawlerReport();
        report.setDomainAsciiLinkCount(domainAsciiLinks);
        report.setDomainAsciiUtfLinkCount(domainAsciUtfLinks);
        report.setDomainLinkCount(domainLinks);
        report.setExtAsciiLinkCount(extAsciiLinks);
        report.setExtLinkCounnt(extLinks);
        report.setInactiveAsciiLinkCount(inactiveAsciiLinkCount);
        report.setInactiveLinkCount(inactiveLinkCount);
        report.setFormFieldCount(formFieldCount);
        report.setEmails(emails);
        report.setAsciiEmails(asciiEmails);

        return URLToWebCrawlerReportConverter.convertURLReportToWebCrawlerReport(inputUrl, report);
    }

    private URLCrawlerReport getHTMLPageReport(String websiteUrl, Set<String> domainLinksSet, CrawlingURLInfo crawlingURLInfo) {
        String url = websiteUrl;
        URLCrawlerReport urlReport = null;

        if (!processedUrl.isProcessed(url)) {
            processedUrl.addProcessedUrl(url);
            int inactiveAsciiLinkCount = 0;
            int inactiveLinkCount = 0;
            int totalLinkInPage = 0;
            int domainAsciiLinks = 0;
            int domainAsciiLinksUtf = 0;
            int domainLinks = 0;
            int extAsciiLinks = 0;
            int extLinks = 0;
            int formFields = 0;

            // email info
            Set<String> asciiEmails = new LinkedHashSet<>();
            Set<String> emails = new LinkedHashSet<>();

            Map<String, Integer> urlToLinkCountMap = null;
            Document document = null;
            Connection.Response response = null;
            if (domainFilter.doFilter(url)) {
                try {
                    if (!ASCIIFormatChecker.check(url)) {
                        url = IDN.toASCII(url);
                        try {
                            document = Jsoup.connect(url).get();

                        } catch (Exception e) {
                            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
                            response = Jsoup.connect(url)
                                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                                    .timeout(10000)
                                    .execute();
                            document = response.parse();
                        }
                        url = IDN.toUnicode(url);
                    } else {
                        try {
                            document = Jsoup.connect(url).get();
                            if ("UTF-8".equalsIgnoreCase(document.charset().name())
                                    || "UTF-16".equalsIgnoreCase(document.charset().name())) {
                                domainAsciiLinksUtf++;
                            }
                        } catch (Exception e) {
                            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
                            response = Jsoup.connect(url)
                                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                                    .timeout(10000)
                                    .execute();
                            document = response.parse();
                            if ("UTF-8".equalsIgnoreCase(document.charset().name())
                                    || "UTF-16".equalsIgnoreCase(document.charset().name())) {
                                domainAsciiLinksUtf++;
                            }
                        }
                    }
                } catch (Exception e1) {
                    LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e1);
                    if (ASCIIFormatChecker.check(url)) {
                        inactiveAsciiLinkCount++;
                    }
                    inactiveLinkCount++;
                }
                if (document != null) {
                    if (ASCIIFormatChecker.check(url)) {
                        domainAsciiLinks++;
                        domainLinksSet.add(url);
                        if (domainLinksSet.size() > 100) {
                            pushData(domainLinksSet, crawlingURLInfo);
                            domainLinksSet.clear();
                        }
                    }
                    domainLinks++;

                    Elements links = document.select("a[href]");
                    totalLinkInPage = links.size();

                    urlToLinkCountMap = new HashMap<>();
                    urlToLinkCountMap.put(url, totalLinkInPage);

                    // email stuff
                    EmailCrawlerReport emailReport = emailCrawler.crawl(document);
                    formFields = fromFieldCrawler.crawl(document);

                    emails = emailReport.getEmails();
                    asciiEmails = emailReport.getAsciiEmails();

                    for (Element link : links) {
                        if ("#".equalsIgnoreCase(link.attr("href"))) {
                            continue;
                        }
                        String linkStr = link.attr("abs:href");
                        if (linkStr == null || (linkStr != null && linkStr.isEmpty()) || "#".equals(linkStr)) {
                            continue;
                        }
                        if (linkStr.charAt(0) == '/') {
                            linkStr = url + linkStr;
                        }
                        try {
                            if (!queue.contains(linkStr)) {
                                queue.put(linkStr);
                            }

                        } catch (Exception e) {
                            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
                        }
                    }
                }
            } else {
                if (ASCIIFormatChecker.check(url)) {
                    extAsciiLinks++;
                }
                extLinks++;
            }
            urlReport = new URLCrawlerReport(domainAsciiLinks, domainAsciiLinksUtf, domainLinks,
                    extAsciiLinks, extLinks, inactiveAsciiLinkCount, inactiveLinkCount,
                    totalLinkInPage, urlToLinkCountMap, asciiEmails, emails, formFields, domainLinksSet);
        }
        return urlReport;
    }


    /*
    This method saves the url that has been crawled into the database.
    */
    private void pushData(Set<String> crawledUrls, CrawlingURLInfo crawlingURLInfo) {
        CrawledUrl crawledUrl;
        Set<CrawledUrl> set = new LinkedHashSet<>();
        for (String url : crawledUrls) {
            crawledUrl = new CrawledUrl();
            crawledUrl.setCrawledUrl(url);
            crawledUrl.setCrawlingURLInfo(crawlingURLInfo);
            set.add(crawledUrl);
        }
        crawledUrlRepository.save(set);
    }


}
