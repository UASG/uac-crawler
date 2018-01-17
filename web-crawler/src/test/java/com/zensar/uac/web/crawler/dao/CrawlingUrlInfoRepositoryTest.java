package com.zensar.uac.web.crawler.dao;

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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by KK48481 on 01-08-2017.
 */
@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
@ComponentScan({"com.zensar.uac.web.crawler"})
@ContextConfiguration
public class CrawlingUrlInfoRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired(required = true)
    private CrawlingUrlInfoRepository crawlingUrlInfoRepository;

    private CrawlingURLInfo crawlingURLInfo1, crawlingURLInfo2, crawlingURLInfo3;

    private LocalDate processStartTime, processStartTime1, processStartTime2, processStartTime3;

    @BeforeMethod
    public void setUp() throws Exception {
        processStartTime = LocalDate.now();
        processStartTime1 = LocalDate.now();
        processStartTime2 = LocalDate.now();
        processStartTime3 = LocalDate.now();
        saveCrawlingUrls();
    }

    private void saveCrawlingUrls() {

        //Saving crawlingURLInfo www.yahoo.com in database with status QUEUE
        crawlingURLInfo1 = new CrawlingURLInfo("www.yahoo.com", CrawlingURLInfo.Status.QUEUE, processStartTime1);
        crawlingURLInfo1 = crawlingUrlInfoRepository.save(crawlingURLInfo1);

        //Saving crawlingURLInfo www.google.com in database with status PROCESSING
        crawlingURLInfo2 = new CrawlingURLInfo("www.google.com", CrawlingURLInfo.Status.PROCESSING, processStartTime2);
        crawlingURLInfo2 = crawlingUrlInfoRepository.save(crawlingURLInfo2);

        //Saving crawlingURLInfo www.zensar.com in database with status COMPLETED
        crawlingURLInfo3 = new CrawlingURLInfo("www.zensar.com", CrawlingURLInfo.Status.COMPLETED, processStartTime3);
        crawlingURLInfo3 = crawlingUrlInfoRepository.save(crawlingURLInfo3);
    }

    private CrawlingURLInfo createNewCrawlingUrlInfo() {
        CrawlingURLInfo crawlingURLInfo = new CrawlingURLInfo("www.gmail.com", CrawlingURLInfo.Status.COMPLETED, processStartTime);
        return crawlingURLInfo;
    }

    private CrawlingURLInfo createEmptyCrawlingUrlInfo() {
        CrawlingURLInfo crawlingURLInfo = new CrawlingURLInfo(null, CrawlingURLInfo.Status.COMPLETED, processStartTime);
        return crawlingURLInfo;
    }

    /*=======================================================================================================
    Test methods for deleteByCrawlingUrl(String crawlingUrl)
    =======================================================================================================*/

    @Test
    public void testDeleteByCrawlingUrl_ValidInput() {
        Long numberOfCrawlingUrlsDeleted = crawlingUrlInfoRepository.deleteByCrawlingUrl("www.yahoo.com");
        assertThat(numberOfCrawlingUrlsDeleted).isNotEqualTo(0L);
        assertThat(numberOfCrawlingUrlsDeleted).isEqualTo(1L);
    }

    @Test
    public void testDeleteByCrawlingUrl_MultipleInputs() {

        //Creating 3 new objects and testing the delete method
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        Long numberOfCrawlingUrlsDeleted = crawlingUrlInfoRepository.deleteByCrawlingUrl("www.gmail.com");
        assertThat(numberOfCrawlingUrlsDeleted).isNotEqualTo(0L);
        assertThat(numberOfCrawlingUrlsDeleted).isEqualTo(3L);
    }

    @Test
    public void testDeleteByCrawlingUrl_InvalidInput() {
        Long numberOfCrawlingUrlsDeleted = crawlingUrlInfoRepository.deleteByCrawlingUrl("www.gmail.com");
        assertThat(numberOfCrawlingUrlsDeleted).isEqualTo(0L);
    }


    /*=======================================================================================================
    Test methods for save(CrawlingURLInfo crawlingURLInfo)
    =======================================================================================================*/

    @Test
    public void testSave_ValidInput() {
        CrawlingURLInfo crawlingURLInfo = createNewCrawlingUrlInfo();
        CrawlingURLInfo savedCrawlingURLInfo = crawlingUrlInfoRepository.save(crawlingURLInfo);
        assertThat(savedCrawlingURLInfo).isNotNull();
        assertThat(savedCrawlingURLInfo).isExactlyInstanceOf(CrawlingURLInfo.class);
        assertThat(savedCrawlingURLInfo.getCrawlingUrl()).isEqualTo("www.gmail.com");
        assertThat(savedCrawlingURLInfo.getStatus()).isEqualTo(CrawlingURLInfo.Status.COMPLETED);
        assertThat(savedCrawlingURLInfo.getProcessStartTime()).isEqualTo(processStartTime);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testSave_InvalidInput() {
        CrawlingURLInfo crawlingURLInfo = createEmptyCrawlingUrlInfo();
        crawlingUrlInfoRepository.save(crawlingURLInfo);
    }


    /*=======================================================================================================
    Test methods for countByCrawlingUrl(String crawlingUrl)
    =======================================================================================================*/

    @Test
    public void testCountByCrawlingUrl_ValidInput() {
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrl("www.yahoo.com");
        assertThat(numberOfCrawlingUrls).isNotEqualTo(0);
        assertThat(numberOfCrawlingUrls).isEqualTo(1);
    }

    @Test
    public void testCountByCrawlingUrl_MultipleInputs() {

        //Creating 3 new objects and testing the count method
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrl("www.gmail.com");
        assertThat(numberOfCrawlingUrls).isNotEqualTo(0);
        assertThat(numberOfCrawlingUrls).isEqualTo(3);
    }

    @Test
    public void testCountByCrawlingUrl_InvalidInput() {
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrl("www.gmail.com");
        assertThat(numberOfCrawlingUrls).isEqualTo(0);
    }


    /*=======================================================================================================
    Test methods for findByCrawlingUrl(String crawlingUrl)
    =======================================================================================================*/

    @Test
    public void testFindByCrawlingUrl_Status_QUEUE() {
        CrawlingURLInfo crawlingURLInfo = crawlingUrlInfoRepository.findByCrawlingUrl("www.yahoo.com");
        assertThat(crawlingURLInfo).isNotNull();
        assertThat(crawlingURLInfo).isExactlyInstanceOf(CrawlingURLInfo.class);
        assertThat(crawlingURLInfo.getCrawlingUrl()).isEqualTo("www.yahoo.com");
        assertThat(crawlingURLInfo.getStatus()).isEqualTo(CrawlingURLInfo.Status.QUEUE);
        assertThat(crawlingURLInfo.getProcessStartTime()).isEqualTo(processStartTime1);
    }

    @Test
    public void testFindByCrawlingUrl_Status_PROCESSING() {
        CrawlingURLInfo crawlingURLInfo = crawlingUrlInfoRepository.findByCrawlingUrl("www.google.com");
        assertThat(crawlingURLInfo).isNotNull();
        assertThat(crawlingURLInfo).isExactlyInstanceOf(CrawlingURLInfo.class);
        assertThat(crawlingURLInfo.getCrawlingUrl()).isEqualTo("www.google.com");
        assertThat(crawlingURLInfo.getStatus()).isEqualTo(CrawlingURLInfo.Status.PROCESSING);
        assertThat(crawlingURLInfo.getProcessStartTime()).isEqualTo(processStartTime2);
    }

    @Test
    public void testFindByCrawlingUrl_Status_COMPLETED() {
        CrawlingURLInfo crawlingURLInfo = crawlingUrlInfoRepository.findByCrawlingUrl("www.zensar.com");
        assertThat(crawlingURLInfo).isNotNull();
        assertThat(crawlingURLInfo).isExactlyInstanceOf(CrawlingURLInfo.class);
        assertThat(crawlingURLInfo.getCrawlingUrl()).isEqualTo("www.zensar.com");
        assertThat(crawlingURLInfo.getStatus()).isEqualTo(CrawlingURLInfo.Status.COMPLETED);
        assertThat(crawlingURLInfo.getProcessStartTime()).isEqualTo(processStartTime3);
    }


    /*=======================================================================================================
    Test methods for countByCrawlingUrlAndStatus(String crawlingUrl, CrawlingURLInfo.Status status)
    =======================================================================================================*/

    @Test
    public void testCountByCrawlingUrlAndStatus_ValidInput_Status_QUEUE() {
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrlAndStatus("www.yahoo.com", CrawlingURLInfo.Status.QUEUE);
        assertThat(numberOfCrawlingUrls).isNotEqualTo(0);
        assertThat(numberOfCrawlingUrls).isEqualTo(1);
    }

    @Test
    public void testCountByCrawlingUrlAndStatus_ValidInput_Status_PROCESSING() {
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrlAndStatus("www.google.com", CrawlingURLInfo.Status.PROCESSING);
        assertThat(numberOfCrawlingUrls).isNotEqualTo(0);
        assertThat(numberOfCrawlingUrls).isEqualTo(1);
    }

    @Test
    public void testCountByCrawlingUrlAndStatus_ValidInput_Status_COMPLETED() {
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrlAndStatus("www.zensar.com", CrawlingURLInfo.Status.COMPLETED);
        assertThat(numberOfCrawlingUrls).isNotEqualTo(0);
        assertThat(numberOfCrawlingUrls).isEqualTo(1);
    }

    @Test
    public void testCountByCrawlingUrlAndStatus_MultipleInputs() {

        //Creating 3 new objects and testing the count method
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        crawlingUrlInfoRepository.save(createNewCrawlingUrlInfo());
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrlAndStatus("www.gmail.com", CrawlingURLInfo.Status.COMPLETED);
        assertThat(numberOfCrawlingUrls).isNotEqualTo(0);
        assertThat(numberOfCrawlingUrls).isEqualTo(3);
    }

    @Test
    public void testCountByCrawlingUrlAndStatus_InvalidCrawlingUrl() {
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrlAndStatus("www.gmail.com", CrawlingURLInfo.Status.COMPLETED);
        assertThat(numberOfCrawlingUrls).isEqualTo(0);
    }

    @Test
    public void testCountByCrawlingUrlAndStatus_InvalidStatus() {
        int numberOfCrawlingUrls = crawlingUrlInfoRepository.countByCrawlingUrlAndStatus("www.yahoo.com", CrawlingURLInfo.Status.COMPLETED);
        assertThat(numberOfCrawlingUrls).isEqualTo(0);
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
    }

    @AfterClass
    public void afterClass() {
        crawlingUrlInfoRepository = null;
    }
}