package com.smartlott.backend.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by greenlucky on 3/31/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NumberAccountServiceTest {

    @Autowired
    private NumberAccountService numberAccountService;

    @Test
    public void activeNumberAccount() throws Exception {
        List<Long> ids = Arrays.asList(Long.valueOf(1), Long.valueOf(2));

       int numberAccounts =  numberAccountService.activeNumberAccount(ids);
        System.out.println(numberAccounts);
    }

    @Test
    public void inActiveNumberAccount() throws Exception {
        List<Long> ids = Arrays.asList(Long.valueOf(1));

        int numberAccounts =  numberAccountService.inActiveNumberAccount(ids);

        Assert.assertEquals(1, numberAccounts);
    }

}