package com.zensar.uac.web.crawler.dao;

import com.zensar.uac.web.crawler.model.UserDetailInfo;
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
 * Created by KK48481 on 31-07-2017.
 */
@EnableAutoConfiguration(exclude={FlywayAutoConfiguration.class})
@TestPropertySource(locations = "classpath:application.properties")
@ComponentScan({"com.zensar.uac.web.crawler"})
@ContextConfiguration
public class UserInfoRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired(required = true)
    private UserInfoRepository userInfoRepository;

    private UserDetailInfo userDetailInfo1, userDetailInfo2;

    private LocalDate requestedOn;

    private List<UserDetailInfo> allUsers;

    @BeforeMethod
    public void setUp() throws Exception {
        requestedOn = LocalDate.now();
        userDetailInfo1 = new UserDetailInfo("Kajal Kukreja", "kajalkukreja694@gmail.com", "Zensar", 9087675454L, requestedOn);
        userDetailInfo2 = new UserDetailInfo("Mayank Pundir", "mayank.pundir@zensar.com", "Zensar", 9099774536L, requestedOn);
        allUsers = new ArrayList<UserDetailInfo>();
    }


    /*=======================================================================================================
    Test methods for save(UserDetailInfo userDetailInfo)
    =======================================================================================================*/

    @Test
    public void testSave_ValidInput() {
        UserDetailInfo savedUserDetailInfo = userInfoRepository.save(userDetailInfo1);
        assertThat(savedUserDetailInfo).isNotNull();
        assertThat(savedUserDetailInfo).isExactlyInstanceOf(UserDetailInfo.class);
        assertThat(savedUserDetailInfo.getUserName()).isEqualTo("Kajal Kukreja");
        assertThat(savedUserDetailInfo.getRequesterEmail()).isEqualTo("kajalkukreja694@gmail.com");
        assertThat(savedUserDetailInfo.getCompanyName()).isEqualTo("Zensar");
        assertThat(savedUserDetailInfo.getContactNumber()).isEqualTo(9087675454L);
        assertThat(savedUserDetailInfo.getRequestedOn()).isEqualTo(requestedOn);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void testSave_InvalidInput() {
        userDetailInfo1.setRequesterEmail(null);
        userDetailInfo1.setCompanyName(null);
        userInfoRepository.save(userDetailInfo1);
    }


    /*=======================================================================================================
    Test methods for findAll()
    =======================================================================================================*/

    @Test
    public void testFindAll_ValidInput() {
        userDetailInfo1 = userInfoRepository.save(userDetailInfo1);
        userDetailInfo2 = userInfoRepository.save(userDetailInfo2);
        allUsers = userInfoRepository.findAll();
        assertThat(allUsers).isNotNull();
        assertThat(allUsers).isExactlyInstanceOf(ArrayList.class);
        assertThat(allUsers).asList().hasSize(2);
        assertThat(userDetailInfo1).isIn(allUsers);
        assertThat(userDetailInfo2).isIn(allUsers);
    }

    @Test
    public void testFindAll_NoInput() {
        allUsers = userInfoRepository.findAll();
        assertThat(allUsers).isNotNull();
        assertThat(allUsers).isExactlyInstanceOf(ArrayList.class);
        assertThat(allUsers).asList().hasSize(0);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        requestedOn = null;
        userDetailInfo1 = null;
        userDetailInfo2 = null;
        allUsers = null;
    }

    @AfterClass
    public void afterClass() {
        userInfoRepository = null;
    }
}