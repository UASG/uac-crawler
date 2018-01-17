package com.zensar.uac.web.crawler.services;

import com.zensar.uac.web.crawler.dao.CrawlingUrlInfoRepository;
import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import com.zensar.uac.web.crawler.services.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mayank.pundir on 07/27/2017.
 */
@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class })
@TestPropertySource(locations = "classpath:application.properties")
@ComponentScan({"com.zensar.uac.web.crawler"})
@ContextConfiguration
public class ReportServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired(required = true)
    private CrawlingUrlInfoRepository crawlingUrlInfoRepository;
    private CrawlingURLInfo crawlingURLInfo;
    private CrawlingURLInfo crawlingURLInfo1;

    private LocalDate processStartTime = LocalDate.now();

   @BeforeMethod
    private void setUp() throws Exception {
        crawlingURLInfo = new CrawlingURLInfo("www.uasg.tech", CrawlingURLInfo.Status.PROCESSING, processStartTime);
        crawlingURLInfo1 = new CrawlingURLInfo("www.zensar.com", CrawlingURLInfo.Status.QUEUE, processStartTime);
        crawlingUrlInfoRepository.save(crawlingURLInfo);
        crawlingUrlInfoRepository.save(crawlingURLInfo1);
    }

        @Test
        public void testCheckCrawlingUrl() {
            ReportService reportService = new ReportServiceImpl(crawlingUrlInfoRepository);
            int returnValue= reportService.checkCrawlingUrl("www.uasg.tech");
            assertThat(returnValue).isNotEqualTo(2);
            assertThat(returnValue).isEqualTo(1);
            returnValue= reportService.checkCrawlingUrl("www.yahoo.com");
            assertThat(returnValue).isNotEqualTo(2);
            assertThat(returnValue).isEqualTo(0);


    }

}

