package com.zensar.uac.web.crawler.services;

import com.zensar.uac.web.crawler.model.UserDetailInfo;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: Interface for User Details
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface UserDetailService {

    /**
     * Saves the user details in database.
     *
     * @param userDetailInfo    the user details
     * @return                  the user details
     */
    UserDetailInfo saveUser(UserDetailInfo userDetailInfo);
}
