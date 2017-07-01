package com.smartlott.backend.api;

import com.smartlott.backend.service.LotteryService;
import com.smartlott.backend.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by lamdevops on 7/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITAwardHandlerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private LotteryService lotteryService;


}