package com.zensar.uac.web.crawler.pages;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Page class for What UASG
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class WhatUASG {

    @Inject
    private Messages messages;

    /**
     * Returns the value for key 'description1' from properties file.
     *
     * @return  the value for key 'description1' from properties file
     */
    public String getDescription1() {
        return String.format(messages.get("description1"));
    }


    /**
     * Returns the value for key 'description2' from properties file.
     *
     * @return  the value for key 'description2' from properties file
     */
    public String getDescription2() {
        return String.format(messages.get("description2"));
    }
}
