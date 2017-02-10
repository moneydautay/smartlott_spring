package com.smartlott.backend.persistence.repositories;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.enums.InvestmentPackageEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class InvestmentPackageRepositoryTest {

    @Autowired
    private InvestmentPackageRepository packageRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(packageRepository);
        Assert.assertNotNull(testName);
    }

    @Test
    public void create() throws Exception {

        InvestmentPackage investmentPackage = new InvestmentPackage(InvestmentPackageEnum.CUSTOMER);

        System.out.println(investmentPackage);

        packageRepository.save(investmentPackage);

        List<InvestmentPackage> aspectPackge = packageRepository.findByEnabled(true);

        Assert.assertEquals("Size of list package must be equals 1", 1, aspectPackge.size());

    }



}