package com.zensar.uac.web.crawler.html;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by srikant.singh on 11/18/2016.
 */

/**
 * Created by Sagar Balai on 08/30/16.
 * Purpose of the class: It will crawl 'inputURL' and give number of Form Fields which are
 * present on that HTML page
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class FormFieldsCrawler {

    /**
     * Returns the number of form fields in the document.
     *
     * @param doc   the document which has to be parsed for emails and email fields
     * @return      the number of form fields in the document
     */
    public int crawl(Document doc) {
        int count = 0;
        int mainCount = 0;
        for (Element element1 : doc.getElementsByTag("form")) {
            for (Element element : element1.getAllElements()) {
                if ("input".equals(element.tagName())) {
                    for (Attribute attribute : element.attributes()) {
                        final boolean typeIsKey = "type".equalsIgnoreCase(attribute.getKey());
                        if ((typeIsKey && "text".equalsIgnoreCase(attribute.getValue()))
                                || (typeIsKey && "email".equalsIgnoreCase(attribute.getValue()))
                                || (typeIsKey && "password".equalsIgnoreCase(attribute.getValue()))
                                || (typeIsKey && "tel".equalsIgnoreCase(attribute.getValue()))) {
                            count = 1;
                        }
                    }
                    mainCount += count;
                    count = 0;
                }
            }
        }
        return mainCount;
    }
}
