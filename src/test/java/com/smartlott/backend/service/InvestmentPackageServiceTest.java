package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.repositories.InvestmentPackageRepository;
import com.smartlott.enums.InvestmentPackageEnum;
import com.smartlott.utils.PageRequestUtils;
import org.junit.Before;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Mrs Hoang on 10/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvestmentPackageServiceTest {

    @Autowired
    private InvestmentPackageService packageService;

    @Before
    public void setupMock() throws Exception{
        Assert.notNull(packageService);
    }

    @Test
    public void getAllPackages() throws Exception{
        PageRequest pageRequest = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "price"));

        Page<InvestmentPackage> investmentPackages = packageService.getAll(true, pageRequest);
        System.out.println(investmentPackages.getContent());
    }

}