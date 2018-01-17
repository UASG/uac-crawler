package com.zensar.uac.web.crawler.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Sagar Balai on 08/30/16.
 * Purpose of the class: Model Class for Crawled Urls
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Entity
@Table(name = "crawled_url")
public class CrawledUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "crawled_url", nullable = false, unique = false)
    private String crawledUrl;

    @ManyToOne(targetEntity = CrawlingURLInfo.class)
    @JoinColumn(name = "crawling_url_info_id", nullable = true)
    private CrawlingURLInfo crawlingURLInfo;

    public CrawledUrl() {
    }

    public CrawledUrl(String crawledUrl, CrawlingURLInfo crawlingURLInfo) {
        this.crawledUrl = crawledUrl;
        this.crawlingURLInfo = crawlingURLInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrawledUrl() {
        return crawledUrl;
    }

    public void setCrawledUrl(String crawledUrl) {
        this.crawledUrl = crawledUrl;
    }

    public CrawlingURLInfo getCrawlingURLInfo() {
        return crawlingURLInfo;
    }

    public void setCrawlingURLInfo(CrawlingURLInfo crawlingURLInfo) {
        this.crawlingURLInfo = crawlingURLInfo;
    }
}
