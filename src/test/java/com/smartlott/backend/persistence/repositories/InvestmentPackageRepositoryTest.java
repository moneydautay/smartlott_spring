package com.smartlott.backend.persistence.repositories;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Cash;
import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.domain.backend.InvestmentPackageCash;
import com.smartlott.enums.CashEnum;
import com.smartlott.enums.InvestmentPackageEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.mockito.Mockito;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InvestmentPackageRepositoryTest {

    @Autowired
    private InvestmentPackageRepository packageRepository;

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private InvestmentPackageCashRepository invCashRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(packageRepository);
        Assert.assertNotNull(testName);
        addCash();
    }

    @Test
    public void create() throws Exception {

        List<Cash> cashs = cashRepository.findAll();
        System.out.println(cashs);

        InvestmentPackage aspect = packageRepository.findOne(1);
        Assert.assertNotNull(aspect);

        Cash cash = new Cash(CashEnum.CASH);
        Cash cash2 = new Cash(CashEnum.INVESTMENT);
        Cash cash3 = new Cash(CashEnum.TRADING);

        InvestmentPackageCash invpc1= new InvestmentPackageCash();
        invpc1.setInvestmentPackage(aspect);
        invpc1.setCash(cash);
        invpc1.setRatevalue(0);

        InvestmentPackageCash invpc2= new InvestmentPackageCash();
        invpc2.setInvestmentPackage(aspect);
        invpc2.setCash(cash2);
        invpc2.setRatevalue(90);

        InvestmentPackageCash invpc3= new InvestmentPackageCash();
        invpc3.setInvestmentPackage(aspect);
        invpc3.setCash(cash3);
        invpc3.setRatevalue(10);


        aspect.setInvestmentPackageCashes(Arrays.asList(invpc1, invpc2, invpc3));

        packageRepository.save(aspect);

        InvestmentPackage actual = packageRepository.findOne(1);
        System.out.println(actual.getInvestmentPackageCashes());
    }

    public void addCash(){
        Cash cash = new Cash(CashEnum.CASH);
        Cash cash2 = new Cash(CashEnum.INVESTMENT);
        Cash cash3 = new Cash(CashEnum.TRADING);
        cashRepository.save(cash);
        cashRepository.save(cash2);
        cashRepository.save(cash3);
    }



}