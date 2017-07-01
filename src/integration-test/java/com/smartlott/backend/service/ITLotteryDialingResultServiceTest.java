package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.*;
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
public class ITLotteryDialingResultServiceTest {

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotteryDialingResultService resultService;

    @Autowired
    private UserService userService;

    @Autowired
    private RewardService rewardService;

    @Test
    public void create() throws Exception {

        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        User user = userService.findOne(1);
        Reward reward = rewardService.getOne(2);

        Lottery lottery = new Lottery();
        lottery.setCoupleOne("11");
        lottery.setCoupleTwo("11");
        lottery.setCoupleThree("11");
        lottery.setCoupleFour("11");
        lottery.setCoupleFive("11");
        lottery.setCoupleSix("11");
        lottery.setBuyBy(user);
        lottery.setLotteryDialing(lotteryDialing);

        lottery = lotteryService.createNewLottery(lottery);

        LotteryDialingResult result = resultService.create(lotteryDialing, user, lottery, reward);
        System.out.println(result);

    }

}