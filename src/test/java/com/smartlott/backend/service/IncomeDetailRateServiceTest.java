package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.IncomeDetailRate;
import com.smartlott.backend.persistence.domain.backend.IncomeRate;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.IncomeDetailRateRepository;
import com.smartlott.intergation.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 12/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class IncomeDetailRateServiceTest extends AbstractIntegrationTest{

    @Autowired
    private IncomeDetailRateService detailRateService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws  Exception{
        Assert.assertNotNull(testName);
        Assert.assertNotNull(detailRateService);
    }

    @Test
    public void testCreate() throws Exception{
        User user = createCustUser(testName.getMethodName(), testName.getMethodName()+"@gmail.com");
        IncomeRate incomeRate = createIncomeRate(user);
        IncomeComponent incomeComponent1 = createIncomeComponent(user, testName.getMethodName(), testName.getMethodName(), 40.7 ,true);
        IncomeComponent incomeComponent2 = createIncomeComponent(user, testName.getMethodName()+"A", testName.getMethodName(),17.0 ,false);
        IncomeComponent incomeComponent3 = createIncomeComponent(user, testName.getMethodName()+"B", testName.getMethodName(), 12.0, false);

        IncomeDetailRate detailRate = new IncomeDetailRate();
        detailRate.setIncomeComponent(incomeComponent1);
        detailRate.setIncomeRate(incomeRate);
        detailRate.setValue(2.0);
        detailRate.setAtLeastOne(false);
        detailRate = detailRateService.create(detailRate);

        Assert.assertNotNull(detailRate.getId());

        IncomeDetailRate detailRate1 = new IncomeDetailRate();
        detailRate1.setIncomeComponent(incomeComponent2);
        detailRate1.setIncomeRate(incomeRate);
        detailRate1.setValue(5.0);
        detailRate1.setAtLeastOne(false);
        detailRateService.create(detailRate1);

        Assert.assertNotNull(detailRate1.getId());

        IncomeDetailRate detailRate2 = new IncomeDetailRate();
        detailRate2.setIncomeComponent(incomeComponent3);
        detailRate2.setIncomeRate(incomeRate);
        detailRate2.setValue(5.0);
        detailRate2.setAtLeastOne(false);
        detailRateService.create(detailRate2);

        Assert.assertNotNull(detailRate2.getId());

        //find income detail rate
        IncomeDetailRate actualDetailRate = detailRateService.getOne(detailRate.getId());

        Assert.assertEquals("Acutal Income detail rate must be equals ", actualDetailRate, detailRate);


        PageRequest request = new PageRequest(0, 15);
        //get income detail rate by income rate
        Page<IncomeDetailRate> aspectIncomeDetail1 = detailRateService.getByIncomeRateId(incomeRate.getId(),request);

        Assert.assertEquals("Income detail 1 must be equals {}",3,aspectIncomeDetail1.getContent().size());

        Page<IncomeDetailRate> aspectIncomeDetail2 = detailRateService.getByIncomeComponentId(incomeComponent1.getId(), request);

        Assert.assertEquals("Income detail 2 must be equals {}",1,aspectIncomeDetail2.getContent().size());

        Page<IncomeDetailRate> aspectIncomeDetail3 = detailRateService.getByIncomeComponentId(incomeComponent2.getId(), request);

        Assert.assertEquals("Income detail 3 must be equals {}",1,aspectIncomeDetail3.getContent().size());

        Page<IncomeDetailRate> aspectIncomeDetail4 = detailRateService.getByIncomeComponentId(incomeComponent3.getId(), request);

        Assert.assertEquals("Income detail 4 must be equals {}",1,aspectIncomeDetail4.getContent().size());


        IncomeDetailRate aspectIncomeDetailOne = detailRateService.getByIncomeComponentIdAndIncomeRateId(incomeComponent1.getId(), incomeRate.getId());

        Assert.assertEquals("AspectIncomeDetailOne must be equals", detailRate, aspectIncomeDetailOne);

        //Update income detail rate
        detailRate1.setValue(5.9);
        IncomeDetailRate actualDetailRateUpdate = detailRateService.update(detailRate1);

        Assert.assertEquals("Actual detail rate{} must be equals {}",detailRate1, actualDetailRateUpdate);

        //Delete income detail rate
        detailRateService.delete(detailRate1.getId());

        //find detail rate 1
        IncomeDetailRate actualDetailDelete = detailRateService.getOne(detailRate1.getId());

        Assert.assertNull(actualDetailDelete);

    }

    @Test
    public void testGetIncomeDetailRateByFormDateAndToDateIncomeRate() throws Exception{

        LocalDateTime fromDate = LocalDateTime.now(Clock.systemDefaultZone());
        LocalDateTime toDate = LocalDateTime.now(Clock.systemDefaultZone());

    }





}