package com.smartlott.backend.api;

import com.smartlott.backend.service.UserCashService;
import com.smartlott.backend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by greenlucky on 5/3/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITTransactionHandlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserCashService userCashService;

    @Autowired
    private UserService userService;

    @Test
    public void transferCash() throws Exception {
        String fromUser = "admin";
        String toUser = "customer1";

        List<?> userCash = userCashService.getUserCashByUserId(Long.valueOf(2));
        userCash.forEach(cash -> {
            System.out.println(cash.toString());
        });

        double amount = 10020.0;
        long cashid = 2;
        Object result = restTemplate.getForObject("/api/transaction/transfer?" +
                "fromUser={fromUser}&toUser={toUser}&amount={amount}&cashId={cashId}",Object.class,
                fromUser, toUser, amount, cashid);
        System.out.println(result);

        userCash = userCashService.getUserCashByUserId(Long.valueOf(2));
        userCash.forEach(cash -> {
            System.out.println(cash.toString());
        });
    }

}