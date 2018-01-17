package com.zensar.uac.web.crawler.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Page class for Message
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class MessagePage {

    @Property
    private String crawlingURL;

    @Property
    @SessionState(create = false)
    private String requestEmail;

    /**
     * Sets the page's activation context before it is rendered.
     * Now, when render request comes for this page, the page will be activated with required fields before it is rendered.
     *
     * @return
     */
    @OnEvent(EventConstants.ACTIVATE)
    Object onActivate() {

        String[] parameters = requestEmail.split("~");
        crawlingURL = parameters[0];
        requestEmail = parameters[1];
        return true;
    }
}
