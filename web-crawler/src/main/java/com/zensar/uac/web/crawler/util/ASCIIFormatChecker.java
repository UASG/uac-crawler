package com.zensar.uac.web.crawler.util;

/**
 * Created by Sagar Balai on 10/16/2016.
 * Purpose of the class: Checking forAsking urls
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */

public class ASCIIFormatChecker {

    //Added a private constructor as this class contains only static methods so no need to instantiate this class.
    private ASCIIFormatChecker() {
    }

    /**
     * Checks whether the given string is ASCII string or not.
     * Returns true for ASCII string and false otherwise.
     *
     * @param input the string which has to be checked for ASCII characters
     * @return      true for ASCII string and false otherwise
     */
    public static boolean check(String input) {
        boolean isAsciiStr = true;
        for (int i = 0; i < input.length(); i++) {
            int c = input.charAt(i);
            if (c > 0x7F) {
                isAsciiStr = false;
                break;
            }
        }
        return isAsciiStr;
    }
}
