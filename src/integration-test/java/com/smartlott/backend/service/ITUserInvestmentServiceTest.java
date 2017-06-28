package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserInvestment;
import com.smartlott.enums.InvestmentPackageEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by lamdevops on 6/25/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITUserInvestmentServiceTest {

    @Autowired
    private UserInvestmentService userInvService;

    @Autowired
    private UserService userService;

    @Before
    public void init() throws Exception {
        Assert.notNull(userInvService);
    }

    @Test
    public void disabledInvestmentPackage() throws Exception {

        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        userInvService.disabledInvestmentPackage(1, now, false);

    }

    @Test
    public void getUserByInvestmentPackageTest() throws Exception {

        User user = userService.findOne(1);


        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());

        UserInvestment userInvestment = new UserInvestment();
        userInvestment.setEnabled(true);
        userInvestment.setFromDate(now.minusDays(1));
        userInvestment.setToDate(now.plusDays(1));
        userInvestment.setUser(user);
        userInvestment.setInvestmentPackage(new InvestmentPackage(InvestmentPackageEnum.GOLDINVEST));

        userInvService.create(userInvestment);

        List<UserInvestment> userInvestments = userInvService.getUserByInvestmentPackage(20, now, true);
        System.out.println(userInvestments);

    }

}