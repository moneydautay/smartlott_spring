package com.smartlott.utils;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.IncomeComponentService;
import com.smartlott.backend.service.LotteryDialingHasIncomeComponentService;
import com.smartlott.backend.service.LotteryDialingService;
import com.smartlott.backend.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Mrs Hoang on 13/01/2017.
 */
public class IncomeComponentAndRewardUtils {

    @Autowired
    private IncomeComponentService componentService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotteryDialingHasIncomeComponentService incomeComponentService;


    public IncomeComponentAndRewardUtils() {

    }

    public void createIncomeComponentAndRewardUtils(User user){
        IncomeComponent incomeComponent1 = new IncomeComponent();
        incomeComponent1.setName("A");
        incomeComponent1.setValue(47.5);
        incomeComponent1.setCreateBy(user);
        incomeComponent1.setEnabled(true);
        incomeComponent1.setDescription("A");
        incomeComponent1 = componentService.create(incomeComponent1);

        IncomeComponent incomeComponent2 = new IncomeComponent();
        incomeComponent2.setName("B");
        incomeComponent2.setValue(20);
        incomeComponent2.setCreateBy(user);
        incomeComponent2.setEnabled(true);
        incomeComponent2.setDescription("B");
        incomeComponent2 = componentService.create(incomeComponent2);

        IncomeComponent incomeComponent3 = new IncomeComponent();
        incomeComponent3.setName("C");
        incomeComponent3.setValue(20);
        incomeComponent3.setCreateBy(user);
        incomeComponent3.setEnabled(true);
        incomeComponent3.setDescription("C");
        incomeComponent3 = componentService.create(incomeComponent3);

        IncomeComponent incomeComponent4 = new IncomeComponent();
        incomeComponent4.setName("D");
        incomeComponent4.setValue(5);
        incomeComponent4.setCreateBy(user);
        incomeComponent4.setEnabled(true);
        incomeComponent4.setDescription("D");
        incomeComponent4 = componentService.create(incomeComponent4);

        IncomeComponent incomeComponent5 = new IncomeComponent();
        incomeComponent5.setName("D");
        incomeComponent5.setValue(5);
        incomeComponent5.setCreateBy(user);
        incomeComponent5.setEnabled(true);
        incomeComponent5.setDescription("D");
        incomeComponent5 = componentService.create(incomeComponent5);

        //create reward
        Reward jeckpots = new Reward();
        jeckpots.setName("Jeckpots");
        jeckpots.setValue(10000000);
        jeckpots.setIncomeComponent(incomeComponent1);
        rewardService.create(jeckpots);

        Reward firstReward = new Reward();
        firstReward.setName("Jeckpots");
        firstReward.setValue(15000);
        firstReward.setIncomeComponent(incomeComponent2);
        rewardService.create(firstReward);

        Reward secondReward = new Reward();
        secondReward.setName("Jeckpots");
        secondReward.setValue(5000);
        secondReward.setIncomeComponent(incomeComponent3);
        rewardService.create(secondReward);

        Reward thirdReward = new Reward();
        thirdReward.setName("Jeckpots");
        thirdReward.setValue(2000);
        thirdReward.setIncomeComponent(incomeComponent4);
        rewardService.create(thirdReward);
    }

    public void createLotDialHasIncome(){

        //get current lottery dialing is opening
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);

        List<IncomeComponent> lstInc = componentService.getAll();

        for (IncomeComponent item : lstInc){
            LotteryDialingHasIncomeComponent loC = new LotteryDialingHasIncomeComponent();
            loC.setValue(0);
            loC.setIncomeComponent(item);
            loC.setLotteryDialing(lotteryDialing);
            incomeComponentService.create(loC);
        }
    }
}
