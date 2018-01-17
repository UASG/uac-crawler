package com.zensar.uac.web.crawler.html.filter;

import com.zensar.uac.web.crawler.util.DomainUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sagar Balai on 08/30/16.
 * Purpose of the class: It will filter URL on the basis of 'allowedDomain' and
 * skipDomains.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class DomainFilter {
    private String domain;
    private String location;

    public synchronized String getDomain() {
        return domain;
    }

    public synchronized void setDomain(String domain) {
        this.domain = domain;
    }

    public synchronized boolean doFilter(String websiteUrl) {
        boolean isDomainUrl = false;
        if (websiteUrl.startsWith(location)) {
            isDomainUrl = true;
        }
        return isDomainUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
