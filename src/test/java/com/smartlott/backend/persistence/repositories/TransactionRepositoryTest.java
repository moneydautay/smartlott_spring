package com.smartlott.backend.persistence.repositories;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.*;
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

/**
 * Created by Mrs Hoang on 08/02/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(transactionRepository);
    }

    @Test
    public void test() throws Exception{

        User user = userRepository.findOne(Long.valueOf(1));

        Investment aInvestment = new Investment();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(new TransactionType(TransactionTypeEnum.BuyInvestmentPackage));
        transaction.setOfUser(user);
        transaction.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));
        transaction.setAmount(10);
        //transaction.setInvestmentPackages(Arrays.asList(aInvestment));

        if(transaction.getTransactionType().isAutoHandle())
            transaction.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.SUCCESS));

        transaction = transactionRepository.save(transaction);
        Assert.assertNotNull(transaction.getId());
        //System.out.println(transaction.getInvestmentPackages());
    }


}