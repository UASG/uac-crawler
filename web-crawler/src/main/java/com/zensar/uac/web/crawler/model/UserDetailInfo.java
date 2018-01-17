package com.zensar.uac.web.crawler.model;

import com.zensar.uac.web.crawler.converter.LocalDateAttributeConverter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Created by srikant.singh on 10/16/2016.
 * Purpose of the class: Model Class for User Details
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Entity
@Table(name = "user_detail_info")
public class UserDetailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "user_name", nullable = false, unique = false)
    private String userName;

    @Basic
    @Column(name = "company_name", nullable = false, unique = false)
    private String companyName;


    @Basic
    @Column(name = "requester_email", nullable = false, unique = false)
    private String requesterEmail;


    @Basic
    @Column(name = "contact_number", nullable = false, unique = false)
    private long contactNumber;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "requested_on", nullable = false)
    private LocalDate requestedOn;

    public UserDetailInfo(String userName, String requesterEmail, String companyName, long contactNumber, LocalDate requestedOn) {
        this.userName = userName;
        this.requesterEmail = requesterEmail;
        this.companyName = companyName;
        this.contactNumber = contactNumber;
        this.requestedOn = requestedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequesterEmail() {
        return requesterEmail;
    }

    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }

    public long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(LocalDate requestedOn) {
        this.requestedOn = requestedOn;
    }
}
