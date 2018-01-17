package com.zensar.uac.web.crawler.model;

import com.zensar.uac.web.crawler.converter.LocalDateAttributeConverter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Created by srikant.singh on 10/20/2016.
 */

/**
 * Created by Sagar Balai on 10/20/2016.
 * Purpose of the class: Model Class for Crawling URL
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Entity
@Table(name = "crawling_url_info")
public class CrawlingURLInfo {

    public enum Status {
        QUEUE, PROCESSING, COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "crawling_url", nullable = false, unique = false)
    private String crawlingUrl;

    @Column(name = "status", nullable = false, updatable = true)
    @Enumerated(EnumType.STRING)
    private CrawlingURLInfo.Status status;

    @Convert(converter = LocalDateAttributeConverter.class)
    @Column(name = "process_start_time", nullable = true)
    private LocalDate processStartTime;

    public CrawlingURLInfo(String crawlingUrl, CrawlingURLInfo.Status status, LocalDate processStartTime) {
        this.crawlingUrl = crawlingUrl;
        this.processStartTime = processStartTime;
        this.status = status;
    }

    public CrawlingURLInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(LocalDate processStartTime) {
        this.processStartTime = processStartTime;
    }

    public String getCrawlingUrl() {
        return crawlingUrl;
    }

    public void setCrawlingUrl(String crawlingUrl) {
        this.crawlingUrl = crawlingUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
