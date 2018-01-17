package com.zensar.uac.web.crawler.pages;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Page class for About Us
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class AboutUs {

    @Inject
    private Messages messages;

    /**
     * Returns the value for key 'description' from properties file.
     *
     * @return  the value for key 'description' from properties file
     */
    public String getDescription() {
        return String.format(messages.get("description"));
    }
}
