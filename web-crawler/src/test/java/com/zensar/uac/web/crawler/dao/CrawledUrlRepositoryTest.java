package com.zensar.uac.web.crawler.dao;

import com.zensar.uac.web.crawler.model.CrawledUrl;
import com.zensar.uac.web.crawler.model.CrawlingURLInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by KK48481 on 01-08-2017.
 */
@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
@ComponentScan({"com.zensar.uac.web.crawler"})
@ContextConfiguration
public class CrawledUrlRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired(required = true)
    private CrawledUrlRepository crawledUrlRepository;

    @Autowired(required = true)
    private CrawlingUrlInfoRepository crawlingUrlInfoRepository;

    private CrawledUrl crawledUrl1, crawledUrl2, crawledUrl3;

    private CrawlingURLInfo crawlingURLInfo1, crawlingURLInfo2, crawlingURLInfo3;

    private LocalDate processStartTime, processStartTime1, processStartTime2, processStartTime3;

    private List<CrawledUrl> crawledUrls, crawlingUrls;

    @BeforeMethod
    public void setUp() throws Exception {
        processStartTime = LocalDate.now();
        processStartTime1 = LocalDate.now();
        processStartTime2 = LocalDate.now();
        processStartTime3 = LocalDate.now();
        crawledUrls = new ArrayList<CrawledUrl>();
        crawlingUrls = new ArrayList<CrawledUrl>();
        saveUrls();
    }

    private void saveUrls() {

        //Saving crawledUrl www.yahoo.com in database with status QUEUE
        crawlingURLInfo1 = new CrawlingURLInfo("www.yahoo.com", CrawlingURLInfo.Status.QUEUE, processStartTime1);
        crawlingURLInfo1 = crawlingUrlInfoRepository.save(crawlingURLInfo1);
        crawledUrl1 = new CrawledUrl("www.yahoo.com", crawlingURLInfo1);
        crawledUrlRepository.save(crawledUrl1);

        //Saving crawledUrl www.google.com in database with status PROCESSING
        crawlingURLInfo2 = new CrawlingURLInfo("www.google.com", CrawlingURLInfo.Status.PROCESSING, processStartTime2);
        crawlingURLInfo2 = crawlingUrlInfoRepository.save(crawlingURLInfo2);
        crawledUrl2 = new CrawledUrl("www.google.com", crawlingURLInfo2);
        crawledUrlRepository.save(crawledUrl2);

        //Saving crawledUrl www.zensar.com in database with status COMPLETED
        crawlingURLInfo3 = new CrawlingURLInfo("www.zensar.com", CrawlingURLInfo.Status.COMPLETED, processStartTime3);
        crawlingURLInfo3 = crawlingUrlInfoRepository.save(crawlingURLInfo3);
        crawledUrl3 = new CrawledUrl("www.zensar.com", crawlingURLInfo3);
        crawledUrlRepository.save(crawledUrl3);
    }

    private CrawledUrl createNewCrawledUrl() {
        CrawlingURLInfo crawlingURLInfo = new CrawlingURLInfo("www.gmail.com", CrawlingURLInfo.Status.COMPLETED, processStartTime);
        crawlingURLInfo = crawlingUrlInfoRepository.save(crawlingURLInfo);
        CrawlingURLInfo savedCrawlingURLInfo = crawlingUrlInfoRepository.save(crawlingURLInfo);
        CrawledUrl crawledUrl = new CrawledUrl("www.gmail.com", savedCrawlingURLInfo);
        return crawledUrl;
    }

    private CrawledUrl createEmptyCrawledUrl() {
        CrawlingURLInfo crawlingURLInfo = new CrawlingURLInfo("www.gmail.com", CrawlingURLInfo.Status.COMPLETED, processStartTime);
        crawlingURLInfo = crawlingUrlInfoRepository.save(crawlingURLInfo);
        CrawlingURLInfo savedCrawlingURLInfo = crawlingUrlInfoRepository.save(crawlingURLInfo);
        CrawledUrl crawledUrl = new CrawledUrl(null, savedCrawlingURLInfo);
        return crawledUrl;
    }

    /*=======================================================================================================
    Test methods for save(CrawledUrl crawledUrl)
    =======================================================================================================*/

    @Test
    public void testSave_ValidInput() {
        CrawledUrl crawledUrl = createNewCrawledUrl();
        CrawledUrl savedCrawledUrl = crawledUrlRepository.save(crawledUrl);
        assertThat(savedCrawledUrl).isNotNull();
        assertThat(savedCrawledUrl).isExactlyInstanceOf(CrawledUrl.class);
        assertThat(savedCrawledUrl.getCrawledUrl()).isEqualTo("www.gmail.com");
        assertThat(savedCrawledUrl.getCrawlingURLInfo().getCrawlingUrl()).isEqualTo("www.gmail.com");
        assertThat(savedCrawledUrl.getCrawlingURLInfo().getStatus()).isEqualTo(CrawlingURLInfo.Status.COMPLETED);
        assertThat(savedCrawledUrl.getCrawlingURLInfo().getProcessStartTime()).isEqualTo(processStartTime);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testSave_InvalidInput() {
        CrawledUrl crawledUrl = createEmptyCrawledUrl();
        crawledUrlRepository.save(crawledUrl);
    }


    /*=======================================================================================================
    Test methods for findByCrawledUrl(String crawledUrl)
    =======================================================================================================*/

    @Test
    public void testFindByCrawledUrl_ValidURL() {
        crawledUrls = crawledUrlRepository.findByCrawledUrl("www.zensar.com");
        assertThat(crawledUrls).isNotNull();
        assertThat(crawledUrls).isExactlyInstanceOf(ArrayList.class);
        assertThat(crawledUrls).asList().hasSize(1);
        assertThat(crawledUrl3).isIn(crawledUrls);
    }

    @Test
    public void testFindByCrawledUrl_InvalidURL() {
        crawledUrls = crawledUrlRepository.findByCrawledUrl("www.twitter.com");
        assertThat(crawledUrls).isNotNull();
        assertThat(crawledUrls).isExactlyInstanceOf(ArrayList.class);
        assertThat(crawledUrls).asList().hasSize(0);
    }


    /*=======================================================================================================
    Test methods for findBycrawlingURLInfo(CrawlingURLInfo crawlingURLInfo)
    =======================================================================================================*/

    @Test
    public void testFindBycrawlingURLInfo_Status_QUEUE() {
        crawlingUrls = crawledUrlRepository.findBycrawlingURLInfo(crawlingURLInfo1);
        assertThat(crawlingUrls).isNotNull();
        assertThat(crawlingUrls).isExactlyInstanceOf(ArrayList.class);
        assertThat(crawlingUrls).asList().hasSize(1);
        assertThat(crawledUrl1).isIn(crawlingUrls);
    }

    @Test
    public void testFindBycrawlingURLInfo_Status_PROCESSING() {
        crawlingUrls = crawledUrlRepository.findBycrawlingURLInfo(crawlingURLInfo2);
        assertThat(crawlingUrls).isNotNull();
        assertThat(crawlingUrls).isExactlyInstanceOf(ArrayList.class);
        assertThat(crawlingUrls).asList().hasSize(1);
        assertThat(crawledUrl2).isIn(crawlingUrls);
    }

    @Test
    public void testFindBycrawlingURLInfo_Status_COMPLETED() {
        crawlingUrls = crawledUrlRepository.findBycrawlingURLInfo(crawlingURLInfo3);
        assertThat(crawlingUrls).isNotNull();
        assertThat(crawlingUrls).isExactlyInstanceOf(ArrayList.class);
        assertThat(crawlingUrls).asList().hasSize(1);
        assertThat(crawledUrl3).isIn(crawlingUrls);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        processStartTime = null;
        processStartTime1 = null;
        processStartTime2 = null;
        processStartTime3 = null;
        crawlingURLInfo1 = null;
        crawlingURLInfo2 = null;
        crawlingURLInfo3 = null;
        crawledUrl1 = null;
        crawledUrl2 = null;
        crawledUrl3 = null;
        crawledUrls = null;
        crawlingUrls = null;
    }

    @AfterClass
    public void afterClass() {
        crawledUrlRepository = null;
        crawlingUrlInfoRepository = null;
    }
}