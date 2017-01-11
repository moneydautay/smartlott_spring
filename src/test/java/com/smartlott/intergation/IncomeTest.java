package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.IncomeDetailRate;
import com.smartlott.backend.persistence.domain.backend.IncomeRate;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.IncomeComponentRepository;
import com.smartlott.backend.persistence.repositories.IncomeDetailRateRepository;
import com.smartlott.backend.persistence.repositories.IncomeRateRepository;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class IncomeTest extends AbstractIntegrationTest{


    @Autowired
    private IncomeComponentRepository incomeComponentRepository;

    @Autowired
    private IncomeRateRepository incomeRateRepository;

    @Autowired
    private IncomeDetailRateRepository incomeDetailRateRepository;


    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeTest() throws Exception{

        //Checking IncomeComponentRepository is not null
        Assert.assertNotNull(incomeComponentRepository);

        //Checking IncomeRateRepository
        Assert.assertNotNull(incomeRateRepository);

        //Checking IncomeDetailRateRepository is not null
        Assert.assertNotNull(incomeDetailRateRepository);
    }

    @Test
    public void incomeTest() throws Exception{

        //create user
        User user = createCustUser(testName);

        //add element to income component
        IncomeComponent incomeComponent = new IncomeComponent();
        incomeComponent.setName("Giai dac biet");
        incomeComponent.setCreateBy(user);

        incomeComponentRepository.save(incomeComponent);

        List<IncomeComponent> incomeComponents = incomeComponentRepository.findAll();

        Assert.assertEquals("Income component must be equal 1", 1, incomeComponents.size());

        //add element to income rate
        IncomeRate incomeRate = new IncomeRate();
        incomeRate.setFromDate(LocalDateTime.now(Clock.systemUTC()));
        incomeRate.setToDate(LocalDateTime.now(Clock.systemUTC()));
        incomeRate.setCreateBy(user);
        System.out.println(incomeRate.toString());
        incomeRateRepository.save(incomeRate);

        List<IncomeRate> incomeRates = incomeRateRepository.findAll();

        Assert.assertEquals("Income rate must be equal 1", 1, incomeRates.size());

        //add income detail rate
        IncomeDetailRate incomeDetailRate = new IncomeDetailRate();
        incomeDetailRate.setIncomeRate(incomeRate);
        incomeDetailRate.setIncomeComponent(incomeComponent);
        incomeDetailRateRepository.save(incomeDetailRate);

        List<IncomeDetailRate> incomeDetailRates = incomeDetailRateRepository.findAll();

        Assert.assertEquals("Income detail rate must be equal 1", 1, incomeDetailRates.size());

    }
}
