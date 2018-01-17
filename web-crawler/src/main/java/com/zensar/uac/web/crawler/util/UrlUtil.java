package com.zensar.uac.web.crawler.util;

import com.zensar.uac.web.crawler.constants.CrawlerConstants;

/**
 * Created by Sagar Balai on 04-10-2016.
 * Purpose of the class: Use  to generate correct urls
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public class UrlUtil {

    //Added a private constructor as this class contains only static methods so no need to instantiate this class.
    private UrlUtil() {
    }

    /**
     * Corrects the website url by adding missing 'http"://' or 'https://'
     *
     * @param inputUrl  the url which has to be corrected
     * @return          the corrected url
     */
    public static String getCorrectHttpUrl(String inputUrl) {
        String url;
        if (!inputUrl.startsWith(CrawlerConstants.HTTP + CrawlerConstants.COLON
                + CrawlerConstants.DOUBLE_BACKSLASH_CHARACTER)) {
            if (!inputUrl.startsWith(CrawlerConstants.HTTPS
                    + CrawlerConstants.COLON
                    + CrawlerConstants.DOUBLE_BACKSLASH_CHARACTER)) {
                url = CrawlerConstants.HTTP + CrawlerConstants.COLON
                        + CrawlerConstants.DOUBLE_BACKSLASH_CHARACTER
                        + inputUrl;
            } else {
                url = inputUrl;
            }
        } else {
            url = inputUrl;
        }
        if (url.endsWith("/")) {
            StringBuilder tempUrl = new StringBuilder("");
            char[] charArray = url.toCharArray();
            for (int i = 0; i < charArray.length - 1; i++) {
                tempUrl.append(charArray[i]);
            }

            url = tempUrl.toString();
        }

        return url;
    }


    /**
     * Generates the filename from given url by removing 'http://' or 'https://' and replacing '.' or ':' with '-'
     *
     * @param url   the url from which filename has to be generated
     * @return      the filename from given url
     */
    public static String getFilenameFromUrl(String url) {
        String fileName = url;
        final String http = CrawlerConstants.HTTP + CrawlerConstants.COLON + CrawlerConstants.DOUBLE_BACKSLASH_CHARACTER;
        final String https = CrawlerConstants.HTTPS + CrawlerConstants.COLON + CrawlerConstants.DOUBLE_BACKSLASH_CHARACTER;

        if (fileName.contains(https)) {
            fileName = fileName.replace(https, CrawlerConstants.BLANK);
        } else if (fileName.contains(http)) {
            fileName = fileName.replace(http, CrawlerConstants.BLANK);
        }
        if (fileName.contains(CrawlerConstants.SINGLE_BACKSLASH_CHARACTER)) {
            fileName = fileName.replace(CrawlerConstants.SINGLE_BACKSLASH_CHARACTER, CrawlerConstants.BLANK);
        }

        fileName = fileName.replace(CrawlerConstants.DOT, CrawlerConstants.DASH);
        fileName = fileName.replace(CrawlerConstants.COLON, CrawlerConstants.DASH);

        return fileName;
    }
}
