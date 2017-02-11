package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.enums.InvestmentPackageEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
public class UserServiceTest {

    private UserService userService;


    @Before
    public void before() throws Exception{
        userService = new UserService();
    }

    @Test
    public void test() throws Exception{
        String introducedKey = userService.createIntroducedKey("admin");
        System.out.println(introducedKey);
        introducedKey = userService.createIntroducedKey("greenlucky");
        System.out.println(introducedKey);
    }

}