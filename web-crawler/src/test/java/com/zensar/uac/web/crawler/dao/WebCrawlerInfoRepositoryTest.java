package com.zensar.uac.web.crawler.dao;

import com.zensar.uac.web.crawler.model.WebCrawlerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by KK48481 on 02-08-2017.
 */
@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
@ComponentScan({"com.zensar.uac.web.crawler"})
@ContextConfiguration
public class WebCrawlerInfoRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired(required = true)
    private WebCrawlerInfoRepository webCrawlerInfoRepository;

    @BeforeMethod
    public void setUp() throws Exception {
        /*
        The object webCrawlerInfo has following values -

        websiteUrl = "www.zensar.com", requesterEmail = "kajal.kukreja@zensar.com", requestedOn = LocalDate.now(),
        numberOfDomainAsciiLink = 532, numberOfDomainLink = 410, numberOfDomainAsciiUtfLink = 410, numberOfExtAsciiLink = 10,
        numberOfExtLink = 16, inactiveAsciiLinkCount = 108, inactiveLinkCount = 40, uaComplianceIndex = 7,
        asciiEmailCount = 25, emailCount = 38, emailFieldsCount = 12, processingTime = 98167, crawledUrls = "~https://zensar.com/"
        */

        WebCrawlerInfo webCrawlerInfo = new WebCrawlerInfo("www.zensar.com", "kajal.kukreja@zensar.com", LocalDate.now(), 532, 410, 410, 10, 16, 108, 40, (short) 7, 25, 38, 12, 98167L, "~https://zensar.com/");
        webCrawlerInfoRepository.save(webCrawlerInfo);
    }

    /*=======================================================================================================
    Test methods for findUacIndexByWebsiteUrl(String websiteUrl)
    =======================================================================================================*/

    @Test
    public void testFindUacIndexByWebsiteUrl_ValidInput() {
        WebCrawlerInfo webCrawlerInfo = webCrawlerInfoRepository.findUacIndexByWebsiteUrl("www.zensar.com");
        assertThat(webCrawlerInfo).isNotNull();
        assertThat(webCrawlerInfo).isExactlyInstanceOf(WebCrawlerInfo.class);

        short uacIndex = webCrawlerInfo.getUaComplianceIndex();
        assertThat(uacIndex).isNotEqualTo((short) 0);
        assertThat(uacIndex).isEqualTo((short) 7);
    }

    @Test
    public void testFindUacIndexByWebsiteUrl_InvalidInput() {
        WebCrawlerInfo webCrawlerInfo = webCrawlerInfoRepository.findUacIndexByWebsiteUrl("www.twitter.com");
        assertThat(webCrawlerInfo).isNull();
    }


    /*=======================================================================================================
    Test methods for findUacIndexByRequesterEmail(String requesterEmail)
    =======================================================================================================*/

    @Test
    public void testFindUacIndexByRequesterEmail_ValidInput() {
        WebCrawlerInfo webCrawlerInfo = webCrawlerInfoRepository.findUacIndexByRequesterEmail("kajal.kukreja@zensar.com");
        assertThat(webCrawlerInfo).isNotNull();
        assertThat(webCrawlerInfo).isExactlyInstanceOf(WebCrawlerInfo.class);

        short uacIndex = webCrawlerInfo.getUaComplianceIndex();
        assertThat(uacIndex).isNotEqualTo((short) 0);
        assertThat(uacIndex).isEqualTo((short) 7);
    }

    @Test
    public void testFindUacIndexByRequesterEmail_InvalidInput() {
        WebCrawlerInfo webCrawlerInfo = webCrawlerInfoRepository.findUacIndexByRequesterEmail("mayank.pundir@zensar.com");
        assertThat(webCrawlerInfo).isNull();
    }

    @AfterClass
    public void afterClass() {
        webCrawlerInfoRepository = null;
    }
}