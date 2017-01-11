package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.IncomeRate;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.intergation.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Created by greenlucky on 1/12/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class IncomeRateServiceTest extends AbstractIntegrationTest{

    @Autowired
    private IncomeRateService incomeRateService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws  Exception{
        Assert.assertNotNull(incomeRateService);
        Assert.assertNotNull(testName);
    }


    @Test
    public void getByFromAndTo() throws Exception {

        User user = createCustUser(testName.getMethodName(), testName.getMethodName()+"@gmail.com");

        LocalDateTime fromDate = LocalDateTime.now(Clock.systemDefaultZone());
        LocalDateTime toDate = LocalDateTime.now(Clock.systemDefaultZone()).minusDays(10);

        IncomeRate incomeRate = new IncomeRate();
        incomeRate.setCreateBy(user);
        incomeRate.setDefault(true);
        incomeRate.setFromDate(fromDate);
        incomeRate.setToDate(toDate);

        incomeRate = incomeRateService.create(incomeRate);

        Assert.assertNotNull(incomeRateService);

        //findy by from and to
        IncomeRate actualIncomeRate = incomeRateService.getByFromDateAndToDate(fromDate, toDate);

        Assert.assertEquals("Actual result income must be equals",incomeRate, actualIncomeRate);

    }

}