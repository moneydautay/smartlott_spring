package com.smartlott.backend.persistence.repositories;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.enums.InvestmentPackageEnum;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.enums.TransactionTypeEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by Mrs Hoang on 08/02/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private InvestmentPackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(transactionRepository);

        InvestmentPackage investmentPackage = new InvestmentPackage(InvestmentPackageEnum.CUSTOMER);

        packageRepository.save(investmentPackage);
    }

    @Test
    public void test() throws Exception{

        User user = userRepository.findOne(Long.valueOf(1));

        InvestmentPackage investmentPackage = new InvestmentPackage(InvestmentPackageEnum.CUSTOMER);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(new TransactionType(TransactionTypeEnum.BuyInvestmentPackage));
        transaction.setOfUser(user);
        transaction.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));
        transaction.setAmount(10);
        transaction.setInvestmentPackages(Arrays.asList(investmentPackage));

        if(transaction.getTransactionType().isAutoHandle())
            transaction.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.SUCCESS));

        transaction = transactionRepository.save(transaction);
        Assert.assertNotNull(transaction.getId());
        System.out.println(transaction.getInvestmentPackages());

        transactionRepository.delete(transaction.getId());

        Transaction aspectTransaction = transactionRepository.findOne(Long.valueOf(1));

        Assert.assertNull(aspectTransaction);

    }


}