package com.zensar.uac.web.crawler.pages;

import com.zensar.uac.web.crawler.constants.CrawlerConstants;
import com.zensar.uac.web.crawler.model.UserDetailInfo;
import com.zensar.uac.web.crawler.services.UserDetailService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.alerts.AlertManager;

import java.time.LocalDate;

/**
 * Created by srikant.singh on 10/06/2016.
 * Purpose of the class: Page class for Contact Us
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class ContactUs {

    @Property
    private String name;

    @Property
    private String contactNumber;

    @Property
    private String companyName;

    @Property
    private String email;


    @Inject
    private UserDetailService userDetailService;

    @Inject
    private AlertManager alertManager;


    /**
     * Returns the page class where the user should be redirected after success event.
     *
     * @return  the page class where the user should be redirected after success event
     */
    @OnEvent(component = "inputForm", value = EventConstants.SUCCESS)
    Object onSuccess() {
        UserDetailInfo userDetailInfo = new UserDetailInfo(name, email, companyName, Long.parseLong(contactNumber), LocalDate.now());
        UserDetailInfo savedUser = userDetailService.saveUser(userDetailInfo);
        if (!savedUser.getUserName().isEmpty()) {
            alertManager.success(CrawlerConstants.CONTACT_US_SUCCESS_MSG);
        }
        return Home.class;
    }


}
