package com.zensar.uac.web.crawler.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.IDN;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.exception.PDFGenerationFailedException;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: Use to gernerate PDF report
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public class PDFGenerator {

    private static final Logger LOGGER = Logger.getLogger(PDFGenerator.class.getName());

    //Added a private constructor as this class contains only static methods so no need to instantiate this class.
    private PDFGenerator() {
    }

    /**
     * Creates a pdf report for the website by using all the available information.
     * Stores the generated report in filesystem so that it can be downloaded in future.
     * Next time, when the user tries to crawl the same website, the existing report will be provided for download.
     *
     * @param report                        the object containing all the information related to the website
     * @throws PDFGenerationFailedException thrown when there is some exception while creating pdf report
     */
    public static void generate(WebCrawlerReport report) throws PDFGenerationFailedException {

        String complianceMessage = "";
        String imagePath = "";

        if (report.getAggregateUAComplianceIndex() == 1) {
            imagePath = "one.png";
        } else if (report.getAggregateUAComplianceIndex() == 2) {
            imagePath = "two.png";
        } else if (report.getAggregateUAComplianceIndex() == 3) {
            imagePath = "three.png";
        } else if (report.getAggregateUAComplianceIndex() == 4) {
            imagePath = "four.png";
        } else if (report.getAggregateUAComplianceIndex() == 5) {
            imagePath = "five.png";
        } else if (report.getAggregateUAComplianceIndex() == 6) {
            imagePath = "six.png";
        } else if (report.getAggregateUAComplianceIndex() == 7) {
            imagePath = "seven.png";
        } else if (report.getAggregateUAComplianceIndex() == 8) {
            imagePath = "eight.png";
        } else if (report.getAggregateUAComplianceIndex() == 9) {
            imagePath = "nine.png";
        } else {
            imagePath = "ten.png";
        }

        if (report.getAggregateUAComplianceIndex() < 3) {

            complianceMessage = " <h2>YOUR WEBSITE IS NOT <span>UA COMPLIANT</span></h2>";
        } else if (report.getAggregateUAComplianceIndex() < 8) {

            complianceMessage = "<h2>YOUR WEBSITE NEEDS SUBSTANTIAL WORK TO MAKE IT <span>UA COMPLIANT</span></h2>";
        } else if (report.getAggregateUAComplianceIndex() < 10) {

            complianceMessage = "<h2>YOUR WEBSITE CAN BE MADE FULLY <span>UA COMPLIANT</span> WITH LITTLE CHANGES</h2>";
        } else {
            complianceMessage = "<h2>YOUR WEBSITE IS FULLY <span>UA COMPLIANT</span></h2>";
        }

        StringBuilder urls = new StringBuilder(25000);
        Set<String> urlset = report.getDomainsLinkSet();
        urlset.remove("null");
        for (String url : urlset) {
            urls.append(" <tr><td>");
            urls.append(url);
            urls.append("</td></tr>");
        }

        String fileName = UrlUtil.getFilenameFromUrl(report.getCrawlingURL());
        String url = report.getCrawlingURL();
        String message = "<p>* This is BETA version and only crawled 25K internal urls.<br/></p>";
        if (!ASCIIFormatChecker.check(report.getCrawlingURL())) {
            url = IDN.toASCII(report.getCrawlingURL()) + "<span style=\"vertical-align :text-top; font-size: 10px;\">#</span>";
            message = "<p>* This is BETA version and only crawled 25K internal urls.<br/>" +
                    "     * For non ASCII urls we are displaying punycode of url in report. </p>";
        }

        String html = "<html><head></head><body><div style=\" width: 100%;\"><span style=\"float: left\"><img class=\"img-responsive\" src=\"PDF/images/zensar.png\"/></span></div><div class=\"reportTop\"><div class=\"timer\"><img src=\"PDF/images/" + imagePath + "\"></img><p class=\"weburl\">" + url + "</p>" + complianceMessage + "</div></div><table class=\"table\"><thead><tr><th>CRITERIA</th><th>COMPLETION FACTOR(Out of 10)</th></tr></thead><tbody><tr><td>HTML Unicode Compliance</td><td style=\"padding-left: 100px;\">" + report.getHtmlUnicodeCompliance() + "</td></tr><tr><td>Form Elements</td><td style=\"padding-left: 100px;\">" + report.getFormElementsCompliance() + "</td></tr><tr><td>Inactive URL</td><td style=\"padding-left: 100px;\">" + report.getInactiveLinkCompliance() + "</td></tr></tbody></table><div sytle=\"padding-top:2px;\">" + message + "</div><div style=\"margin-top:5em;align:center;\"><table class=\"table\" ><thead><tr><th>Summary Table</th></tr></thead><tbody><tr id=\"myTr0myTable129\"><td>URL</td><td>" + url + "</td></tr><tr id=\"myTr1myTable129\"><td>Requestor Email</td><td>" + report.getEmailID() + "</td></tr><tr id=\"myTr2myTable129\"><td>Number of Domain ASCII Link</td><td>" + report.getDomainAsciiLinkCount() + "</td></tr><tr id=\"myTr3myTable129\"><td>Number of Domain Link</td><td>" + report.getDomainLinkCount() + "</td></tr><tr id=\"myTr4myTable129\"><td>Number of Ext ASCII Link</td><td>" + report.getExtAsciiLinkCount() + "</td></tr><tr id=\"myTr5myTable129\"><td>Number of Ext Links</td><td>" + report.getExtLinkCount() + "</td></tr><tr id=\"myTr6myTable129\"><td>Number of Domain Inactive link</td><td>" + report.getInactiveLinkCount() + "</td></tr></tbody></table></div><div><table class=\"table\"><thead><tr><th>ASCII Links</th></tr></thead>" + urls + "</table></div></body></html>";

        String css = "html {    margin: 0;    padding: 0;} body {	margin: 0;    padding: 0;    font-family: \"Lato\";    font-size: 15px;} h1, h2, h3, h4, h5, ul {    margin: 0;    padding: 0;}a {    color: #3690e0;    text-decoration: none;}.reportTop {    background: #3690e0 center top no-repeat;    background-size: 100% 710px;    float: left;    width: 100%;    text-align: center;    padding-top: 20px;}.reportTop h2 {    font-family: \"LatoWebLight\";}.reportTop h2, .reportTop h2 span {    color: #fff;    font-size: 30px;    display: block;}.timer {    text-align: center;}.weburl {    text-align: center;    color: #fff;    font-family: \"LatoWebBlack\";    text-transform: uppercase;    font-size: 18px;    margin-bottom: 40px;}.table > tbody > tr > td, .table > tfoot > tr > td, .table > thead > tr > th, .table > tfoot > tr > th, .table > thead > tr > td {    padding: 18px;    vertical-align: top;    border-top: 1px solid #ddd;} .table tbody tr:first-child td {    border-top: 0;}thead {    background-color: #f6f6f6;    border-bottom: none;}.table > tbody > tr > td {    color: #7d7d7d;}.table > tbody > tr > td.fail {    color: #f84e4e;    font-size: 15px;}.table > tbody > tr > td.pass {    color: #75bf4f;} .table {    margin-bottom: 0; margin-top : 25px;}";


        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("PDF/" + fileName.replace(".", "-").replace(":", "-") + ".pdf"));
            document.open();
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

            InputStream is = new ByteArrayInputStream(html.getBytes());

            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);

            writer.setPageEvent(new DraftBackground());

            CSSResolver cssResolver = new StyleAttrCSSResolver();
            CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(css.getBytes()));
            cssResolver.addCss(cssFile);


            CssResolverPipeline cssPipe = new CssResolverPipeline(cssResolver,
                    new HtmlPipeline(htmlContext, pdf));

            XMLWorker worker = new XMLWorker(cssPipe, true);
            XMLParser p = new XMLParser(worker);
            p.parse(is, Charset.forName("UTF-8"));
            p.parse(is);

            document.close();

        } catch (IOException | DocumentException e) {
            LOGGER.log(Level.SEVERE, CrawlerConstants.EXCEPTION, e);
            throw new PDFGenerationFailedException("Failed to generate PDF.");
        }
    }

}
