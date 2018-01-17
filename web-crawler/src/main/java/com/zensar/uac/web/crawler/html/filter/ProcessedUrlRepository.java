package com.zensar.uac.web.crawler.html.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sagar Balai on 08/30/16.
 * Purpose of the class: It will contain set of processed web site urls.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class ProcessedUrlRepository {
    Set<String> processedUrlSet = new HashSet<>();

    public void addProcessedUrl(String url) {
        String processedUrl = url;
        if (processedUrlSet == null) {
            processedUrlSet = new HashSet<>();
        }
        if (processedUrl.charAt(processedUrl.length() - 1) == '/' || processedUrl.charAt(processedUrl.length() - 1) == '\\') {
            processedUrl = processedUrl.substring(0, processedUrl.length() - 1);
        }
        processedUrlSet.add(processedUrl);
    }

    public boolean isProcessed(String url) {
        String processedUrl = url;
        if (processedUrl.charAt(processedUrl.length() - 1) == '/' || processedUrl.charAt(processedUrl.length() - 1) == '\\') {
            processedUrl = processedUrl.substring(0, processedUrl.length() - 1);
        }
        return processedUrlSet.contains(processedUrl);
    }

    public Set<String> getProcessedUrlSet() {
        return processedUrlSet;
    }
}
