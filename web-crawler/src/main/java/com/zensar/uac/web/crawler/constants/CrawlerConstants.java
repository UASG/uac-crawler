package com.zensar.uac.web.crawler.constants;

/**
 * Created by srikant.singh on 10/12/2016.
 * Purpose of the class: Crawler Constant
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public class CrawlerConstants {
    public static final int MAX_UA_COMPLIANCE = 9;
    public static final int MIN_UA_COMPLIANCE = 1;
    public static final int AVERAGE_UA_COMPLIANCE = 5;
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String COLON = ":";
    public static final String DOUBLE_BACKSLASH_CHARACTER = "//";
    public static final String SINGLE_BACKSLASH_CHARACTER = "/";
    public static final String WWW = "www";
    public static final String DOT = ".";
    public static final String DASH = "-";
    public static final String BLANK = "";
    public static final String CONTACT_US_SUCCESS_MSG = "Web Crawler Support Team will contact you in next 24 hours";
    public static final String PASS = "pass";
    public static final String PASSED = "Passed";
    public static final String FAIL = "fail";
    public static final String FAILED = "Failed";
    public static final String EXCEPTION = "Exception";

    //Added a private constructor as this class contains only constants so no need to instantiate this class.
    private CrawlerConstants() {
    }
}
