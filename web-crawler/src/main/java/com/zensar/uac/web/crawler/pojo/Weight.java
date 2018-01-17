package com.zensar.uac.web.crawler.pojo;

/**
 * Created by srikant.singh on 09/28/2016.
 */

/**
 * Created by srikant.singh on 09/28/2016.
 * Purpose of the class: POJO for Weight
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
public class Weight {

    // aggregate compliance weights
    private float aggregateDomainLinkWeight;
    private float aggregateDomainUtfLinkWeight;
    private float aggregateInactiveLinkWeight;
    private float aggregateEmailTextWeight;
    private float aggregateFormFieldWeight;

    // link compliance weights
    private float linkDomainLinkWeight;
    private float linkInactiveLinkWeight;

    // emails compliance weights
    private float emailTextWeight;
    private float formFieldWeight;

    public float getAggregateDomainLinkWeight() {
        return aggregateDomainLinkWeight;
    }

    public void setAggregateDomainLinkWeight(float aggregateDomainLinkWeight) {
        this.aggregateDomainLinkWeight = aggregateDomainLinkWeight;
    }

    public float getAggregateDomainUtfLinkWeight() {
        return aggregateDomainUtfLinkWeight;
    }

    public void setAggregateDomainUtfLinkWeight(float aggregateDomainUtfLinkWeight) {
        this.aggregateDomainUtfLinkWeight = aggregateDomainUtfLinkWeight;
    }

    public float getAggregateEmailTextWeight() {
        return aggregateEmailTextWeight;
    }

    public void setAggregateEmailTextWeight(float aggregateEmailTextWeight) {
        this.aggregateEmailTextWeight = aggregateEmailTextWeight;
    }

    public float getAggregateFormFieldWeight() {
        return aggregateFormFieldWeight;
    }

    public void setAggregateFormFieldWeight(float aggregateFormFieldWeight) {
        this.aggregateFormFieldWeight = aggregateFormFieldWeight;
    }

    public float getAggregateInactiveLinkWeight() {
        return aggregateInactiveLinkWeight;
    }

    public void setAggregateInactiveLinkWeight(float aggregateInactiveLinkWeight) {
        this.aggregateInactiveLinkWeight = aggregateInactiveLinkWeight;
    }

    public float getLinkDomainLinkWeight() {
        return linkDomainLinkWeight;
    }

    public void setLinkDomainLinkWeight(float linkDomainLinkWeight) {
        this.linkDomainLinkWeight = linkDomainLinkWeight;
    }

    public float getLinkInactiveLinkWeight() {
        return linkInactiveLinkWeight;
    }

    public void setLinkInactiveLinkWeight(float linkInactiveLinkWeight) {
        this.linkInactiveLinkWeight = linkInactiveLinkWeight;
    }

    public float getEmailTextWeight() {
        return emailTextWeight;
    }

    public void setEmailTextWeight(float emailTextWeight) {
        this.emailTextWeight = emailTextWeight;
    }

    public float getFormFieldWeight() {
        return formFieldWeight;
    }

    public void setFormFieldWeight(float formFieldWeight) {
        this.formFieldWeight = formFieldWeight;
    }
}
