package com.zensar.uac.web.crawler.html;

import com.zensar.uac.web.crawler.pojo.EmailCrawlerReport;
import com.zensar.uac.web.crawler.util.ASCIIFormatChecker;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ssrikant.singh on 09/26/2016.
 * Purpose of the class: It will crawl 'inputURL' and give number of Emails which are
 * present on that HTML page
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class EmailCrawler {

    /**
     * Returns the report containing number of emails and email fields in the document.
     *
     * @param doc   the document which has to be parsed for emails and email fields
     * @return      the report containing emails and email fields in the document
     */
    public EmailCrawlerReport crawl(Document doc) {
        int emailFieldCount = 0;
        Set<String> asciiEmails = new HashSet<>();
        Set<String> emails = new HashSet<>();
        Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
        Matcher matcher = p.matcher(doc.text());
        while (matcher.find()) {
            if (ASCIIFormatChecker.check(matcher.group())) {
                asciiEmails.add(matcher.group());
            }
            emails.add(matcher.group());

        }
        emailFieldCount = getEmailsInputFieldsCount(doc);

        return new EmailCrawlerReport(asciiEmails, emails, emailFieldCount);
    }

    /**
     * Returns the number of email fields in the document.
     *
     * @param   doc     the document which has to be parsed for email fields
     * @return          the number of email fields in the document
     */
    private int getEmailsInputFieldsCount(Document doc) {
        int count = 0;
        boolean breakFromOuterLoop = false;
        for (Element element : doc.getAllElements()) {
            for (Attribute attribute : element.attributes()) {
                if (!"label".equals(element.tagName()) && "email".equalsIgnoreCase(attribute.getKey())) {
                    count = count + 1;
                    breakFromOuterLoop = true;
                    break;
                } else if ("Enter your email".equalsIgnoreCase(attribute.getValue())
                        || "Enter email".equalsIgnoreCase(attribute.getValue())) {
                    count = count + 1;
                }
            }
            if (breakFromOuterLoop) {
                break;
            }
        }
        return count;
    }
}