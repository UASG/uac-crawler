package com.zensar.uac.web.crawler.pages;

import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.services.impl.EmailServiceImpl;
import com.zensar.uac.web.crawler.util.PDFGenerator;
import com.zensar.uac.web.crawler.util.UrlUtil;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Page class for Report
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class Report {

    private static final Logger LOGGER = Logger.getLogger(Report.class.getName());

    @SessionState(create = false)
    private WebCrawlerReport webCrawlerReport;

    @Property
    @Persist
    private int uAComplianceIndex;

    @Property
    private int domainLinkCompliance;

    @Property
    @SessionState(create = false)
    private String crawlingURL;

    @Property
    private int emailTextCompliance;

    @Property
    private int emailFieldCompliance;

    @Property
    private int htmlUnicodeCompliance;

    @Property
    private int inactiveLinkCompliance;

    @Property
    private int linkComplianceIndex;

    @Property
    private int emailComplianceIndex;

    @Property
    private int formElementsCompliance;

    @Property
    private String domainLinkComplianceStatus = CrawlerConstants.FAILED;

    @Property
    private String inactiveLinkComplianceStatus = CrawlerConstants.FAILED;

    @Property
    private String emailTextComplianceStatus = CrawlerConstants.FAILED;

    @Property
    private String emailFieldComplianceStatus = CrawlerConstants.FAILED;

    @Property
    private String domainLinkComplianceClass = CrawlerConstants.FAIL;

    @Property
    private String emailTextComplianceClass = CrawlerConstants.FAIL;

    @Property
    private String emailFieldComplianceClass = CrawlerConstants.FAIL;

    @Property
    private String inactiveLinkComplianceClass = CrawlerConstants.FAIL;

    @Property
    private String viewReportData;

    @Property
    @SessionState(create = false)
    private String requestEmail;

    @Inject
    Block one;
    @Inject
    Block two;
    @Inject
    Block three;
    @Inject
    Block four;
    @Inject
    Block five;
    @Inject
    Block six;
    @Inject
    Block seven;
    @Inject
    Block eight;
    @Inject
    Block nine;
    @Inject
    Block ten;
    @Inject
    Block on;
    @Inject
    Block tw;
    @Inject
    Block th;
    @Inject
    Block fo;
    @Inject
    Block fi;
    @Inject
    Block si;
    @Inject
    Block se;
    @Inject
    Block ei;
    @Inject
    Block ni;
    @Inject
    Block te;
    @Inject
    Block o1;
    @Inject
    Block t2;
    @Inject
    Block t3;
    @Inject
    Block f4;
    @Inject
    Block f5;
    @Inject
    Block s6;
    @Inject
    Block s7;
    @Inject
    Block e8;
    @Inject
    Block n9;
    @Inject
    Block t10;

    /**
     * Sets the page's activation context before it is rendered.
     * Now, when render request comes for this page, the page will be activated with required fields before it is rendered.
     * and the page will also send pdf report as email attachment.
     *
     * @return
     */
    @OnEvent(EventConstants.ACTIVATE)
    Object onActivate() {
        if (webCrawlerReport == null) {
            return Home.class;
        } else if (webCrawlerReport.getDomainAsciiLinkCount() == -10) {

            requestEmail = webCrawlerReport.getCrawlingURL() + "~" + webCrawlerReport.getEmailID();

            return MessagePage.class;
        }
        uAComplianceIndex = webCrawlerReport.getAggregateUAComplianceIndex();
        crawlingURL = webCrawlerReport.getCrawlingURL();
        domainLinkCompliance = webCrawlerReport.getDomainLinkCompliance();
        emailTextCompliance = webCrawlerReport.getEmailTextCompliance();
        inactiveLinkCompliance = webCrawlerReport.getInactiveLinkCompliance();
        htmlUnicodeCompliance = webCrawlerReport.getHtmlUnicodeCompliance();
        linkComplianceIndex = webCrawlerReport.getLinkComplianceIndex();
        formElementsCompliance = webCrawlerReport.getFormElementsCompliance();
        emailComplianceIndex = webCrawlerReport.getEmailComplianceIndex();

        viewReportData = getReportData();

        if (domainLinkCompliance == 10) {
            domainLinkComplianceStatus = CrawlerConstants.PASSED;
            domainLinkComplianceClass = CrawlerConstants.PASS;
        }
        if (inactiveLinkCompliance == 10) {
            inactiveLinkComplianceStatus = CrawlerConstants.PASSED;
            inactiveLinkComplianceClass = CrawlerConstants.PASS;
        }
        if (emailTextCompliance == 10) {
            emailTextComplianceStatus = CrawlerConstants.PASSED;
            emailTextComplianceClass = CrawlerConstants.PASS;
        }
        if (emailFieldCompliance == 10) {
            emailFieldComplianceStatus = CrawlerConstants.PASSED;
            emailFieldComplianceClass = CrawlerConstants.PASS;

        }

        try {
            PDFGenerator.generate(webCrawlerReport);
            FileSystem fileSystems = FileSystems.getDefault();
            Path filePath = fileSystems.getPath("PDF/" + UrlUtil.getFilenameFromUrl(crawlingURL) + ".pdf").normalize();
            File file = filePath.toFile();
            EmailServiceImpl.sendEmail(file, webCrawlerReport.getEmailID(), "Hi Please find the attached web Crawler analyser report", "Web Crawler Analyser Report");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
        }
        return true;
    }

    public Block getCase() {
        if (uAComplianceIndex == 1) {
            return one;
        } else if (uAComplianceIndex == 2) {
            return two;
        } else if (uAComplianceIndex == 3) {
            return three;
        } else if (uAComplianceIndex == 4) {
            return four;
        } else if (uAComplianceIndex == 5) {
            return five;
        } else if (uAComplianceIndex == 6) {
            return six;
        } else if (uAComplianceIndex == 7) {
            return seven;
        } else if (uAComplianceIndex == 8) {
            return eight;
        } else if (uAComplianceIndex == 9) {
            return nine;
        } else if (uAComplianceIndex == 10) {
            return ten;
        } else {
            return null;
        }
    }

    public Block getInternalsLinks() {
        if (linkComplianceIndex == 1) {
            return on;
        } else if (linkComplianceIndex == 2) {
            return tw;
        } else if (linkComplianceIndex == 3) {
            return th;
        } else if (linkComplianceIndex == 4) {
            return fo;
        } else if (linkComplianceIndex == 5) {
            return fi;
        } else if (linkComplianceIndex == 6) {
            return si;
        } else if (linkComplianceIndex == 7) {
            return se;
        } else if (linkComplianceIndex == 8) {
            return ei;
        } else if (linkComplianceIndex == 9) {
            return ni;
        } else if (linkComplianceIndex == 10) {
            return te;
        } else {
            return null;
        }
    }

    public Block getFormElements() {
        if (formElementsCompliance == 1) {
            return o1;
        } else if (formElementsCompliance == 2) {
            return t2;
        } else if (formElementsCompliance == 3) {
            return t3;
        } else if (formElementsCompliance == 4) {
            return f4;
        } else if (formElementsCompliance == 5) {
            return f5;
        } else if (formElementsCompliance == 6) {
            return s6;
        } else if (formElementsCompliance == 7) {
            return s7;
        } else if (formElementsCompliance == 8) {
            return e8;
        } else if (formElementsCompliance == 9) {
            return n9;
        } else if (formElementsCompliance == 10) {
            return t10;
        } else {
            return null;
        }
    }


    private String getReportData() {
        return "URL-" +
                webCrawlerReport.getCrawlingURL() + "#Requestor Email-" + webCrawlerReport.getEmailID() + "#Number of Domain ASCII Link-"
                + webCrawlerReport.getDomainAsciiLinkCount() + "#Number of Domain Link-" + webCrawlerReport.getDomainLinkCount() + "#Number of Ext ASCII Link-"
                + webCrawlerReport.getExtAsciiLinkCount() + "#Number of Ext Links-" + webCrawlerReport.getExtLinkCount() + "#Number of Domain Inactive link-"
                + webCrawlerReport.getInactiveLinkCount() + "#Email Counts-" + webCrawlerReport.getAsciiEmailTextCount() + "#Email Fields Counts-"
                + webCrawlerReport.getFormFieldsCount();
    }

    public boolean getHtmlUnicodeCriteria() {
        return htmlUnicodeCompliance == 10 ? true : false;
    }

    public boolean getInactiveLinkCriteria() {
        return inactiveLinkCompliance == 10 ? true : false;
    }

    public boolean getEmailTextCriteria() {
        return emailTextCompliance == 10 ? true : false;
    }

    public boolean getFormElementCriteria() {
        return formElementsCompliance == 10 ? true : false;
    }

    /**
     * Returns the file to be downloaded when export link is clicked.
     *
     * @return  the file to be downloaded when export link is clicked
     */
    @OnEvent(component = "exportLink", value = EventConstants.ACTION)
    public StreamResponse onExportClick() {
        return getExport();
    }

    /*
    This method gives pdf report for website so that it can be downloaded by user
    */
    private StreamResponse getExport() {
        return new StreamResponse() {
            @Override
            public void prepareResponse(Response response) {
                setHeaders(response, UrlUtil.getFilenameFromUrl(crawlingURL));
            }

            @Override
            public InputStream getStream() throws IOException {
                FileSystem fileSystems = FileSystems.getDefault();
                Path filePath = fileSystems.getPath("PDF/" + UrlUtil.getFilenameFromUrl(crawlingURL) + ".pdf").normalize();
                File file = filePath.toFile();
                return new FileInputStream(file);
            }

            @Override
            public String getContentType() {
                return "text/pdf";
            }
        };
    }

    /*
    This method is used for setting headers required for downloading the report
    */
    public static void setHeaders(Response response, String fileName) {
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=" + '"' + fileName + ".pdf" + '"');
    }

}
