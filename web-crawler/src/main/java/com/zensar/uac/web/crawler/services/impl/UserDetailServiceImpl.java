package com.zensar.uac.web.crawler.services.impl;

import com.zensar.uac.web.crawler.dao.UserInfoRepository;
import com.zensar.uac.web.crawler.model.UserDetailInfo;
import com.zensar.uac.web.crawler.services.UserDetailService;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: Class for User Details
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class UserDetailServiceImpl implements UserDetailService {

    @Inject
    private UserInfoRepository userInfoRepository;

    public UserDetailInfo saveUser(UserDetailInfo userDetailInfo) {
        UserDetailInfo savedUserInfo = userInfoRepository.save(userDetailInfo);
        EmailServiceImpl.sendEmail(null, "harshal.jawale@zensar.com", "Hi Support" + savedUserInfo.getUserName() + " want to contact you. Please contact him ", "Web Cralwer Contact Us ");
        return savedUserInfo;
    }
}
