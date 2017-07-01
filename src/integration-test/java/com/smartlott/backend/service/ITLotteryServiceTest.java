package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import org.junit.Test;
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
public class ITLotteryServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Test
    public void createNewLottery() throws Exception {
        Lottery lottery = new Lottery();

    }

}