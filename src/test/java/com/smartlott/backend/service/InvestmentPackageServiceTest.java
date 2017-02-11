package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.repositories.InvestmentPackageRepository;
import com.smartlott.enums.InvestmentPackageEnum;
import org.junit.Before;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

/**
 * Created by Mrs Hoang on 10/02/2017.
 */
public class InvestmentPackageServiceTest {

    private InvestmentPackageService packageService;
    private InvestmentPackageRepository investmentPackageRepository;

    @Before
    public void setupMock() throws Exception{
        investmentPackageRepository = Mockito.mock(InvestmentPackageRepository.class);
        packageService = new InvestmentPackageService(investmentPackageRepository);



    }

    @Test
    public void createInvestmentPackage() throws  Exception{

        InvestmentPackage investmentPackage = new InvestmentPackage(InvestmentPackageEnum.CUSTOMER);

        when(investmentPackageRepository.findOne(investmentPackage.getId())).thenReturn(null);

        doAnswer(returnsFirstArg()).when(investmentPackageRepository).save(any(InvestmentPackage.class));


        InvestmentPackage aspect = packageService.create(investmentPackage);
        assertEquals(aspect, investmentPackage);
        assertNotNull(aspect);
    }

    @Test
    public void createInvestmentWithEmptyName() throws Exception{
        InvestmentPackage investmentPackage = new InvestmentPackage();

        investmentPackageRepository.save(investmentPackage);
    }

}