package com.zensar.uac.web.crawler.dao;

import com.zensar.uac.web.crawler.model.UserDetailInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: This class deals with persisting User Details info.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public interface UserInfoRepository extends CrudRepository<UserDetailInfo, Long> {

    /**
     * Saves the user details in database.
     *
     * @param userDetailInfo    the user details
     * @return                  the user details
     */
    UserDetailInfo save(UserDetailInfo userDetailInfo);


    /**
     * Returns the list of all users from database.
     *
     * @return  the list of all users
     */
    List<UserDetailInfo> findAll();
}
