package com.zensar.uac.web.crawler.complexity.calculation.strategy.bracket.impl;

import com.zensar.uac.web.crawler.complexity.calculation.strategy.bracket.BracketComplexityCalculationStrategy;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.pojo.Weight;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.stereotype.Component;

/**
 * Created by Sagar Balai on 10/24/2016.
 * Purpose of the class: This class is use for strategy for bracket complexity calculation.
 * Sponsor: www.zensar.com
 * License: This code is released under GPL. You are free to make changes to the code as long as you provide
 * attribution to the Sponsor.
 */
@Component
public class BracketComplexityCalculationStrategyImpl implements BracketComplexityCalculationStrategy {

    private Weight weight;

    /*
    Constructor for BracketComplexityCalculationStrategyImpl.
    The weight argument is used to initialize a newly created BracketComplexityCalculationStrategyImpl object.
    It contains the weights that will be used for calculations.
    */
    public BracketComplexityCalculationStrategyImpl(@Inject Weight weight) {
        this.weight = weight;
    }

    @Override
    public int calculateAggregateCompliance(WebCrawlerReport webCrawlerReport) {
        float complexity = 0;

        complexity = calculateDomainLinkCompliance(webCrawlerReport.getDomainAsciiLinkCount(), webCrawlerReport.getDomainLinkCount()) * weight.getAggregateDomainLinkWeight()
                + calculateDomainUtfLinkCompliance(webCrawlerReport.getDomainAsciiLinkCount(), webCrawlerReport.getDomainAsciiUtfLinkCount()) * weight.getAggregateDomainUtfLinkWeight()
                + calculateComplexityFactor(webCrawlerReport.getExtLinkCount()) * weight.getAggregateInactiveLinkWeight()
                + calculateEmailTextCompliance(webCrawlerReport.getAsciiEmailTextCount(), webCrawlerReport.getEmailCount()) * weight.getAggregateEmailTextWeight()
                + formElementsCompliance(webCrawlerReport.getDomainLinkCount(), webCrawlerReport.getFormFieldsCount()) * weight.getAggregateFormFieldWeight();

        return Math.round(complexity);
    }

    @Override
    public int calculateDomainLinkCompliance(int domainAsciiLinkCount, int domainLinkCount) {
        if (domainAsciiLinkCount == domainLinkCount) {
            if (domainAsciiLinkCount == 0) {
                return 10;
            }
            return 1;
        }
        return 10 - (int) (((float) domainAsciiLinkCount / (float) domainLinkCount) * 10);
    }

    @Override
    public int calculateDomainUtfLinkCompliance(int domainAsciiLinkCount, int domainUtfLinkCount) {
        if (domainAsciiLinkCount == domainUtfLinkCount) {
            return 10;
        }
        return (int) (((float) domainUtfLinkCount / (float) domainAsciiLinkCount) * 10);
    }

    @Override
    public int calculateEmailTextCompliance(int asciiEmailCount, int emailCount) {
        if (asciiEmailCount == emailCount) {
            if (asciiEmailCount == 0) {
                return 10;
            }
            return 1;
        }
        return 10 - (int) (((float) asciiEmailCount / (float) emailCount) * 10);
    }

    @Override
    public int calculateEmailFieldCompliance(int emailFieldCount) {
        return calculateComplexityFactor(emailFieldCount);
    }

    @Override
    public int calculateInactiveLinkCompliance(int inactiveLinkCount) {
        return calculateComplexityFactor(inactiveLinkCount);
    }


    // link compliance
    @Override
    public int calculateLinkCompliance(WebCrawlerReport webCrawlerReport) {
        float complexity = 0;

        complexity = calculateDomainUtfLinkCompliance(webCrawlerReport.getDomainAsciiLinkCount(), webCrawlerReport.getDomainAsciiUtfLinkCount()) * weight.getAggregateDomainUtfLinkWeight()
                + calculateComplexityFactor(webCrawlerReport.getExtLinkCount()) * weight.getLinkInactiveLinkWeight();
        return Math.round(complexity);
    }

    @Override
    public int calculateEmailCompliance(WebCrawlerReport webCrawlerReport) {
        float complexity = 0;

        complexity = calculateComplexityFactor(webCrawlerReport.getFormFieldsCount()) * weight.getFormFieldWeight()
                + calculateEmailTextCompliance(webCrawlerReport.getAsciiEmailTextCount(), webCrawlerReport.getEmailCount()) * weight.getEmailTextWeight();
        return Math.round(complexity);
    }

    /*
    This method takes total number of entities whose complexity factor has to be calculated
    and returns the complexity factor of the given entity.
    */
    private int calculateComplexityFactor(int entityCount) {
        int complexityFactor = 10 - entityCount;

        if (entityCount >= 10) {
            complexityFactor = 1;
        }
        return complexityFactor;
    }

    @Override
    public int formElementsCompliance(int domainLinkCount, int formFieldsCount) {
        int complianceFactor = 0;
        if (formFieldsCount == 0) {
            complianceFactor = 10;
            return complianceFactor;
        }

        int percentageOfFormFields = Math.round(((float) formFieldsCount / (float) domainLinkCount) * 100);
        if (percentageOfFormFields >= 10) {
            complianceFactor = 1;
        } else if (percentageOfFormFields > 0 && percentageOfFormFields < 10) {
            complianceFactor = (10 - percentageOfFormFields) + 1;
        }

        return complianceFactor;
    }
}