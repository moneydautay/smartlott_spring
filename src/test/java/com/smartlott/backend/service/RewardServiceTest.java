package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.Reward;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.RewardRepository;
import com.smartlott.intergation.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mrs Hoang on 12/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class RewardServiceTest extends AbstractIntegrationTest{

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

        IncomeComponent inc1 = createIncomeComponent(user, testName.getMethodName()+"A", testName.getMethodName(), 47.7, true);
        IncomeComponent inc2 = createIncomeComponent(user, testName.getMethodName()+"B", testName.getMethodName(), 17.7, true);
        IncomeComponent inc3 = createIncomeComponent(user, testName.getMethodName()+"C", testName.getMethodName(), 10, true);
        IncomeComponent inc4 = createIncomeComponent(user, testName.getMethodName()+"D", testName.getMethodName(),  5, true);
        IncomeComponent inc5 = createIncomeComponent(user, testName.getMethodName()+"E", testName.getMethodName(), 2, true);
        IncomeComponent inc6 = createIncomeComponent(user, testName.getMethodName()+"F", testName.getMethodName(), 2, true);

        Reward reward1 = new Reward();
        reward1.setName(testName.getMethodName());
        reward1.setIncomeComponent(inc1);
        reward1.setJeckpots(true);

        reward1 = rewardService.create(reward1);

        Assert.assertNotNull(reward1);

        Reward reward2 = new Reward();
        reward2.setName(testName.getMethodName());
        reward2.setIncomeComponent(inc2);
        reward2.setJeckpots(true);

        reward2 = rewardService.create(reward2);

        Assert.assertNotNull(reward2);

        Reward reward3 = new Reward();
        reward3.setName(testName.getMethodName());
        reward3.setIncomeComponent(inc2);
        reward3.setJeckpots(true);

        reward3 = rewardService.create(reward3);

        Assert.assertNotNull(reward3);

        Reward reward4 = new Reward();
        reward4.setName(testName.getMethodName());
        reward4.setIncomeComponent(inc2);
        reward4.setJeckpots(true);

        reward4 = rewardService.create(reward4);

        Assert.assertNotNull(reward4);

        Reward reward = rewardService.getOne(1);
        reward.setName("Jeckpots");
        reward.setValue(47);
        Reward actualReward = rewardService.update(reward);
        Assert.assertEquals("Actual reward must be equals", actualReward, reward);

        rewardService.delete(1);
        Reward rewardA = rewardService.getOne(1);

        Assert.assertNull(rewardA);

        List<Reward> rewards = rewardService.getAll();

        Assert.assertEquals("List rewards must be 3", 3, rewards.size());
    }
}