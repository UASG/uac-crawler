package com.zensar.uac.web.crawler.config;

import com.zensar.uac.web.crawler.pojo.Weight;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by srikant.singh on 09/28/2016.
 * Purpose of the class: Weightage configuration
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Configuration
@PropertySource("classpath:criteria.properties")
public class WeightageConfig {

    @Value("${aggregate.domain.link.weight}")
    private String aggregateDomainLinkWeight;

    @Value("${aggregate.domain.utf8.link.weight}")
    private String aggregateDomainUtfLinkWeight;

    @Value("${aggregate.email.text.weight}")
    private String aggregateEmailTextWeight;

    @Value("${aggregate.email.field.weight}")
    private String aggregateEmailFieldWeight;

    @Value("${aggregate.inactive.link.weight}")
    private String aggregateInactiveLinkWeight;


    @Value("${links_domain.link.weight}")
    private String linkDomainLinkWeight;

    @Value("${links_inactive.link.weight}")
    private String linkInactiveLinkWeight;


    @Value("${emails.email.text.weight}")
    private String emailTextWeight;

    @Value("${emails.form.field.weight}")
    private String formFieldWeight;


    @Bean
    public Weight weight() {
        Weight weight = new Weight();
        // aggregate compliance weights
        weight.setAggregateDomainLinkWeight(Float.parseFloat(aggregateDomainLinkWeight));
        weight.setAggregateFormFieldWeight(Float.parseFloat(aggregateEmailFieldWeight));
        weight.setAggregateEmailTextWeight(Float.parseFloat(aggregateEmailTextWeight));
        weight.setAggregateInactiveLinkWeight(Float.parseFloat(aggregateInactiveLinkWeight));

        // link compliance weight
        weight.setLinkDomainLinkWeight(Float.parseFloat(linkDomainLinkWeight));
        weight.setAggregateDomainUtfLinkWeight(Float.parseFloat(aggregateDomainUtfLinkWeight));
        weight.setLinkInactiveLinkWeight(Float.parseFloat(linkInactiveLinkWeight));

        // email compliance weight
        weight.setEmailTextWeight(Float.parseFloat(emailTextWeight));
        weight.setFormFieldWeight(Float.parseFloat(formFieldWeight));

        return weight;
    }
}
