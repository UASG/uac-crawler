package com.zensar.uac.web.crawler.complexity.calculation.strategy.bracket;

import com.zensar.uac.web.crawler.complexity.calculation.strategy.bracket.impl.BracketComplexityCalculationStrategyImpl;
import com.zensar.uac.web.crawler.domain.WebCrawlerReport;
import com.zensar.uac.web.crawler.pojo.Weight;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by KK48481 on 25-07-2017.
 */
@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
@ComponentScan({"com.zensar.uac.web.crawler"})
@ContextConfiguration
public class BracketComplexityCalculationStrategyTest extends AbstractTransactionalTestNGSpringContextTests {

    private Weight weight;

    private BracketComplexityCalculationStrategy bracketComplexityCalculationStrategy;

    private WebCrawlerReport webCrawlerReport;

    @BeforeMethod
    public void setUp() throws Exception {
        weight = new Weight();

        // aggregate compliance weights
        weight.setAggregateDomainLinkWeight(0.01f);
        weight.setAggregateDomainUtfLinkWeight(0.85f);
        weight.setAggregateInactiveLinkWeight(0.03f);
        weight.setAggregateEmailTextWeight(0.01f);
        weight.setAggregateFormFieldWeight(0.1f);

        // link compliance weights
        weight.setLinkDomainLinkWeight(0.9f);
        weight.setLinkInactiveLinkWeight(0.1f);

        // emails compliance weights
        weight.setEmailTextWeight(0.99f);
        weight.setFormFieldWeight(0.01f);

        bracketComplexityCalculationStrategy = new BracketComplexityCalculationStrategyImpl(weight);
    }

    /*=======================================================================================================
    Test methods for calculateDomainLinkCompliance(int domainAsciiLinkCount, int domainLinkCount)
    =======================================================================================================*/

    @Test
    public void testCalculateDomainLinkCompliance_EqualLinkCounts() {
        //Here, domainAsciiLinkCount = 752 and domainLinkCount = 752 so it should return 1
        int domainLinkCompliance = bracketComplexityCalculationStrategy.calculateDomainLinkCompliance(752, 752);
        assertThat(domainLinkCompliance).isEqualTo(1);
    }

    @Test
    public void testCalculateDomainLinkCompliance_BothLinkCountsZero() {
        //Here, domainAsciiLinkCount = 0 and domainLinkCount = 0 so it should return 10
        int domainLinkCompliance = bracketComplexityCalculationStrategy.calculateDomainLinkCompliance(0, 0);
        assertThat(domainLinkCompliance).isEqualTo(10);
    }

    @Test
    public void testCalculateDomainLinkCompliance_DifferentLinkCounts_ValidInput() {
        //Here, domainAsciiLinkCount = 752 and domainLinkCount = 785 so it should return 1
        int domainLinkCompliance = bracketComplexityCalculationStrategy.calculateDomainLinkCompliance(752, 785);
        assertThat(domainLinkCompliance).isEqualTo(1);
    }

    @Test
    public void testCalculateDomainLinkCompliance_DifferentLinkCounts_InvalidInput() {
        //Here, domainAsciiLinkCount = 752 and domainLinkCount = 225 so it should return -23
        int domainLinkCompliance = bracketComplexityCalculationStrategy.calculateDomainLinkCompliance(752, 225);
        assertThat(domainLinkCompliance).isEqualTo(-23);
    }


    /*=======================================================================================================
    Test methods for calculateDomainUtfLinkCompliance(int domainAsciiLinkCount, int domainUtfLinkCount)
    =======================================================================================================*/

    @Test
    public void testCalculateDomainUtfLinkCompliance_EqualLinkCounts() {
        //Here, domainAsciiLinkCount = 752 and domainUtfLinkCount = 752 so it should return 10
        int domainUtfLinkCompliance = bracketComplexityCalculationStrategy.calculateDomainUtfLinkCompliance(752, 752);
        assertThat(domainUtfLinkCompliance).isEqualTo(10);
    }

    @Test
    public void testCalculateDomainUtfLinkCompliance_DifferentLinkCounts_ValidInput() {
        //Here, domainAsciiLinkCount = 532 and domainUtfLinkCount = 785 so it should return 14
        int domainUtfLinkCompliance = bracketComplexityCalculationStrategy.calculateDomainUtfLinkCompliance(532, 785);
        assertThat(domainUtfLinkCompliance).isEqualTo(14);
    }

    @Test
    public void testCalculateDomainUtfLinkCompliance_DifferentLinkCounts_InvalidInput() {
        //Here, domainAsciiLinkCount = 752 and domainUtfLinkCount = 225 so it should return 2
        int domainUtfLinkCompliance = bracketComplexityCalculationStrategy.calculateDomainUtfLinkCompliance(752, 225);
        assertThat(domainUtfLinkCompliance).isEqualTo(2);
    }


    /*=======================================================================================================
    Test methods for calculateEmailTextCompliance(int asciiEmailCount, int emailCount)
    =======================================================================================================*/

    @Test
    public void testCalculateEmailTextCompliance_EqualEmailCounts() {
        //Here, asciiEmailCount = 16 and emailCount = 16 so it should return 1
        int emailTextCompliance = bracketComplexityCalculationStrategy.calculateEmailTextCompliance(16, 16);
        assertThat(emailTextCompliance).isEqualTo(1);
    }

    @Test
    public void testCalculateEmailTextCompliance_BothEmailCountsZero() {
        //Here, asciiEmailCount = 0 and emailCount = 0 so it should return 10
        int emailTextCompliance = bracketComplexityCalculationStrategy.calculateEmailTextCompliance(0, 0);
        assertThat(emailTextCompliance).isEqualTo(10);
    }

    @Test
    public void testCalculateEmailTextCompliance_DifferentEmailCounts_ValidInput() {
        //Here, asciiEmailCount = 7 and emailCount = 15 so it should return 6
        int emailTextCompliance = bracketComplexityCalculationStrategy.calculateEmailTextCompliance(7, 15);
        assertThat(emailTextCompliance).isEqualTo(6);
    }

    @Test
    public void testCalculateEmailTextCompliance_DifferentEmailCounts_InvalidInput() {
        //Here, asciiEmailCount = 18 and emailCount = 6 so it should return -20
        int emailTextCompliance = bracketComplexityCalculationStrategy.calculateEmailTextCompliance(18, 6);
        assertThat(emailTextCompliance).isEqualTo(-20);
    }


    /*=======================================================================================================
    Test methods for calculateEmailFieldCompliance(int emailFieldCount)
    =======================================================================================================*/

    //emailFieldCompliance will be treated as 10 when there are no email fields (i.e., emailFieldCount = 0)
    @Test
    public void testCalculateEmailFieldCompliance_CountEqualToZero() {
        //Here, emailFieldCount = 0 so it should return 10
        int emailFieldCompliance = bracketComplexityCalculationStrategy.calculateEmailFieldCompliance(0);
        assertThat(emailFieldCompliance).isEqualTo(10);
    }

    @Test
    public void testCalculateEmailFieldCompliance_CountLessThanTen() {
        //Here, emailFieldCount = 4 so it should return 6
        int emailFieldCompliance = bracketComplexityCalculationStrategy.calculateEmailFieldCompliance(4);
        assertThat(emailFieldCompliance).isEqualTo(6);
    }

    @Test
    public void testCalculateEmailFieldCompliance_CountGreaterThanTen() {
        //Here, emailFieldCount = 13 so it should return 1
        int emailFieldCompliance = bracketComplexityCalculationStrategy.calculateEmailFieldCompliance(13);
        assertThat(emailFieldCompliance).isEqualTo(1);
    }

    @Test
    public void testCalculateEmailFieldCompliance_CountEqualToTen() {
        //Here, emailFieldCount = 10 so it should return 1
        int emailFieldCompliance = bracketComplexityCalculationStrategy.calculateEmailFieldCompliance(10);
        assertThat(emailFieldCompliance).isEqualTo(1);
    }


    /*=======================================================================================================
    Test methods for calculateInactiveLinkCompliance(int inactiveLinkCount)
    =======================================================================================================*/

    //inactiveLinkCompliance will be treated as 10 when there are no inactive links/dead links (i.e., inactiveLinkCount = 0)
    @Test
    public void testCalculateInactiveLinkCompliance_CountEqualToZero() {
        //Here, inactiveLinkCount = 0 so it should return 10
        int inactiveLinkCompliance = bracketComplexityCalculationStrategy.calculateInactiveLinkCompliance(0);
        assertThat(inactiveLinkCompliance).isEqualTo(10);
    }

    @Test
    public void testCalculateInactiveLinkCompliance_CountLessThanTen() {
        //Here, inactiveLinkCount = 4 so it should return 6
        int inactiveLinkCompliance = bracketComplexityCalculationStrategy.calculateInactiveLinkCompliance(4);
        assertThat(inactiveLinkCompliance).isEqualTo(6);
    }

    @Test
    public void testCalculateInactiveLinkCompliance_CountGreaterThanTen() {
        //Here, inactiveLinkCount = 13 so it should return 1
        int inactiveLinkCompliance = bracketComplexityCalculationStrategy.calculateInactiveLinkCompliance(13);
        assertThat(inactiveLinkCompliance).isEqualTo(1);
    }

    @Test
    public void testCalculateInactiveLinkCompliance_CountEqualToTen() {
        //Here, inactiveLinkCount = 10 so it should return 1
        int inactiveLinkCompliance = bracketComplexityCalculationStrategy.calculateInactiveLinkCompliance(10);
        assertThat(inactiveLinkCompliance).isEqualTo(1);
    }


    /*=======================================================================================================
    Test methods for calculateLinkCompliance(WebCrawlerReport webCrawlerReport)
    =======================================================================================================*/

    @Test
    public void testCalculateLinkCompliance_ValidInput() {
        /*
        The object webCrawlerReport has following values -

        url = "www.zensar.com", domainAsciiLinkCount = 532, domainAsciiUtfLinkCount = 410, domainLinkCount = 410, extAsciiLinkCount = 10
        extLinkCount = 16, inactiveAsciiLinkCount = 108, inactiveLinkCount = 40, asciiEmailTextCount = 25,
        emailCount = 38, formFieldsCount = 15, domainsLinkSet = new HashSet<>();
        */

        webCrawlerReport = new WebCrawlerReport("www.zensar.com", 532, 410, 410, 10, 16, 108, 40, 25, 38, 15, new HashSet<>());
        int linkCompliance = bracketComplexityCalculationStrategy.calculateLinkCompliance(webCrawlerReport);
        assertThat(linkCompliance).isEqualTo(6);
    }

    @Test
    public void testCalculateLinkCompliance_InvalidInput() {
        /*
        The object webCrawlerReport has following values -

        url = "", domainAsciiLinkCount = 0, domainAsciiUtfLinkCount = 0, domainLinkCount = 0, extAsciiLinkCount = 0
        extLinkCount = 0, inactiveAsciiLinkCount = 0, inactiveLinkCount = 0, asciiEmailTextCount = 0,
        emailCount = 0, formFieldsCount = 0, domainsLinkSet = new HashSet<>();
        */

        webCrawlerReport = new WebCrawlerReport("", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, new HashSet<>());
        int linkCompliance = bracketComplexityCalculationStrategy.calculateLinkCompliance(webCrawlerReport);
        assertThat(linkCompliance).isEqualTo(10);
    }

    /*=======================================================================================================
    Test methods for calculateEmailCompliance(WebCrawlerReport webCrawlerReport)
    =======================================================================================================*/

    @Test
    public void testCalculateEmailCompliance_ValidInput() {
        /*
        The object webCrawlerReport has following values -

        url = "www.zensar.com", domainAsciiLinkCount = 532, domainAsciiUtfLinkCount = 410, domainLinkCount = 410, extAsciiLinkCount = 10
        extLinkCount = 16, inactiveAsciiLinkCount = 108, inactiveLinkCount = 40, asciiEmailTextCount = 25,
        emailCount = 38, formFieldsCount = 15, domainsLinkSet = new HashSet<>();
        */

        webCrawlerReport = new WebCrawlerReport("www.zensar.com", 532, 410, 410, 10, 16, 108, 40, 25, 38, 15, new HashSet<>());
        int emailCompliance = bracketComplexityCalculationStrategy.calculateEmailCompliance(webCrawlerReport);
        assertThat(emailCompliance).isEqualTo(4);
    }

    @Test
    public void testCalculateEmailCompliance_InvalidInput() {
        /*
        The object webCrawlerReport has following values -

        url = "", domainAsciiLinkCount = 0, domainAsciiUtfLinkCount = 0, domainLinkCount = 0, extAsciiLinkCount = 0
        extLinkCount = 0, inactiveAsciiLinkCount = 0, inactiveLinkCount = 0, asciiEmailTextCount = 0,
        emailCount = 0, formFieldsCount = 0, domainsLinkSet = new HashSet<>();
        */

        webCrawlerReport = new WebCrawlerReport("", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, new HashSet<>());
        int emailCompliance = bracketComplexityCalculationStrategy.calculateEmailCompliance(webCrawlerReport);
        assertThat(emailCompliance).isEqualTo(10);
    }


    /*=======================================================================================================
    Test methods for formElementsCompliance(int domainLinkCount, int formFieldsCount)
    =======================================================================================================*/

    @Test
    public void testFormElementsCompliance_FormFieldsCountZero() {
        //Here, domainLinkCount = 752 and formFieldsCount = 0 so it should return 10
        int complianceFactor = bracketComplexityCalculationStrategy.formElementsCompliance(752, 0);
        assertThat(complianceFactor).isEqualTo(10);
    }

    @Test
    public void testFormElementsCompliance_PercentageOfFormFieldsGreaterThanTen() {
        //Here, domainLinkCount = 752 and formFieldsCount = 115 so it should return 1
        int complianceFactor = bracketComplexityCalculationStrategy.formElementsCompliance(752, 115);
        assertThat(complianceFactor).isEqualTo(1);
    }

    @Test
    public void testFormElementsCompliance_PercentageOfFormFieldsEqualToTen() {
        //Here, domainLinkCount = 752 and formFieldsCount = 75 so it should return 1
        int complianceFactor = bracketComplexityCalculationStrategy.formElementsCompliance(752, 75);
        assertThat(complianceFactor).isEqualTo(1);
    }

    @Test
    public void testFormElementsCompliance_PercentageOfFormFieldsEqualToOne() {
        //Here, domainLinkCount = 752 and formFieldsCount = 7 so it should return 10
        int complianceFactor = bracketComplexityCalculationStrategy.formElementsCompliance(752, 7);
        assertThat(complianceFactor).isEqualTo(10);
    }


    /*=======================================================================================================
    Test methods for calculateAggregateCompliance(WebCrawlerReport webCrawlerReport)
    =======================================================================================================*/

    @Test
    public void testCalculateAggregateCompliance_ValidInput() {
        /*
        The object webCrawlerReport has following values -

        url = "www.zensar.com", domainAsciiLinkCount = 532, domainAsciiUtfLinkCount = 410, domainLinkCount = 410, extAsciiLinkCount = 10
        extLinkCount = 16, inactiveAsciiLinkCount = 108, inactiveLinkCount = 40, asciiEmailTextCount = 25,
        emailCount = 38, formFieldsCount = 15, domainsLinkSet = new HashSet<>();
        */

        webCrawlerReport = new WebCrawlerReport("www.zensar.com", 532, 410, 410, 10, 16, 108, 40, 25, 38, 15, new HashSet<>());
        int aggregateCompliance = bracketComplexityCalculationStrategy.calculateAggregateCompliance(webCrawlerReport);
        assertThat(aggregateCompliance).isEqualTo(7);
    }

    @Test
    public void testCalculateAggregateCompliance_InvalidInput() {
        /*
        The object webCrawlerReport has following values -

        url = "", domainAsciiLinkCount = 0, domainAsciiUtfLinkCount = 0, domainLinkCount = 0, extAsciiLinkCount = 0
        extLinkCount = 0, inactiveAsciiLinkCount = 0, inactiveLinkCount = 0, asciiEmailTextCount = 0,
        emailCount = 0, formFieldsCount = 0, domainsLinkSet = new HashSet<>();
        */

        webCrawlerReport = new WebCrawlerReport("", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, new HashSet<>());
        int aggregateCompliance = bracketComplexityCalculationStrategy.calculateAggregateCompliance(webCrawlerReport);
        assertThat(aggregateCompliance).isEqualTo(10);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        webCrawlerReport = null;
        bracketComplexityCalculationStrategy = null;
        weight = null;
    }
}