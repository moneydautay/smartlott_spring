package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Reward;
import com.smartlott.backend.persistence.domain.backend.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Mrs Hoang on 12/01/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardServiceTest{

    @Autowired
    private RewardService rewardService;

    @Autowired
    private UserService userService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception{

        Assert.assertNotNull(rewardService);
        Assert.assertNotNull(testName);
    }

    @Test
    public void testCreate() throws Exception{

        User user = userService.findOne(1);

        Reward reward1 = new Reward();
        reward1.setName(testName.getMethodName());
        reward1.setJeckpots(false);
        reward1.setAccumulation(false);

        reward1 = rewardService.create(reward1);

        Assert.assertNotNull(reward1);

        Reward reward2 = new Reward();
        reward2.setName(testName.getMethodName());
        reward2.setJeckpots(false);

        reward2 = rewardService.create(reward2);

        Assert.assertNotNull(reward2);

        Reward reward3 = new Reward();
        reward3.setName(testName.getMethodName());
        reward3.setJeckpots(false);

        reward3 = rewardService.create(reward3);

        Assert.assertNotNull(reward3);

        Reward reward4 = new Reward();
        reward4.setName(testName.getMethodName());
        reward4.setJeckpots(false);

        reward4 = rewardService.create(reward4);

        Assert.assertNotNull(reward4);

        Reward reward = rewardService.getOne(1);
        reward.setName("Jeckpots");
        reward.setAccumulation(true);
        reward.setValue(47);
        Reward actualReward = rewardService.update(reward);
        Assert.assertEquals("Actual reward must be equals", actualReward, reward);

        rewardService.delete(1);
        Reward rewardA = rewardService.getOne(1);

        Assert.assertNull(rewardA);

        List<Reward> rewards = rewardService.getAll();

        Assert.assertEquals("List rewards must be 3", 3, rewards.size());
    }

    @Test
    public void getRewardByIncomeComponentId() throws Exception{

    }

    @Test
    public void getReward() throws Exception{
        Reward reward = rewardService.getOne(1);
        System.out.println(reward);
    }
}