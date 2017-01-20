package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.LotteryDialingHasIncomeComponentService;
import com.smartlott.backend.service.LotteryDialingService;
import com.smartlott.backend.service.LotterySerivce;
import com.smartlott.backend.service.RewardService;
import com.smartlott.enums.LotteryTypeEnum;
import com.smartlott.utils.MathUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by Mrs Hoang on 17/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class CalculatedRewardTest {

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotterySerivce lotterySerivce;

    @Autowired
    private LotteryDialingHasIncomeComponentService componentService;

    @Autowired
    private RewardService rewardService;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(dialingService);
        Assert.assertNotNull(lotterySerivce);
        Assert.assertNotNull(componentService);
    }

    @Test
    public void test40Lottery() throws Exception{

        CreateTest(40);
    }

    @Test
    public void test150Lottery() throws Exception{

        CreateTest(150);
    }

    @Test
    public void test2000Lottery() throws Exception{

        CreateTest(2000);
    }

    private String getRandom(){
        Random cop = new Random();
        String cop1 = String.valueOf(cop.nextInt(45));
        if(cop1.length() < 2)
            cop1+="0"+cop1;
        if(cop1.length() > 2)
            cop1=cop1.substring(0,2);
        return cop1;
    }

    private void CreateTest(int numberLottery) throws Exception{
        LotteryType lotteryType = new LotteryType(LotteryTypeEnum.TYPE1);
        //get current lottery dialing
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        Assert.assertNotNull(lotteryDialing);

        for (int i = 0; i < numberLottery; i++){

            Lottery lottery = new Lottery();
            lottery.setCoupleOne(getRandom());
            lottery.setCoupleTwo(getRandom());
            lottery.setCoupleThree(getRandom());
            lottery.setCoupleFour(getRandom());
            lottery.setCoupleFive(getRandom());
            lottery.setCoupleSix(getRandom());
            lottery.setLotteryType(lotteryType);

            lottery = lotterySerivce.createNewLottery(lottery);

            componentService.saveIncomeForLotteryDialing(lotteryDialing.getId(),1);
        }

        List<Lottery> lotteries = lotterySerivce.getAll();

        Assert.assertEquals("Number of lottery must be equals "+numberLottery, lotteries.size(),numberLottery);

        List<LotteryDialingHasIncomeComponent> components = componentService.getAll();
        double totalValue = 0;
        for (LotteryDialingHasIncomeComponent component : components){

            System.out.println("Dialing com: "+ component.getIncomeComponent().getName() + ": val "+ component.getValue());
            totalValue += component.getValue();
            double expectValue = component.getIncomeComponent().getValue()*numberLottery/100;
            expectValue = MathUtils.round(expectValue,4);
            Assert.assertEquals(expectValue,component.getValue(),numberLottery);

            //Get reward give by income component
            Reward reward = rewardService.getRewardByIncomeComponentId(component.getIncomeComponent().getId());
            System.out.println();
            System.out.println();
            System.out.println("[========================================================]");
            System.out.println("[========================================================]");
            if(reward != null) {
                int numberReward = (int) (component.getValue() / reward.getValue());
                if (numberReward > 0) {
                    System.out.println("Default reward: " + reward.getName() + ": " + reward.getValue());
                    System.out.println("Comp reward: " + ": " + component.getValue());
                    System.out.println(reward.getName() + ": " + numberReward);
                }
                else
                    System.out.println("Reward name: " + reward.getName() + ": " + reward.getDefaultNumericReward());
            }
            System.out.println("[========================================================]");
            System.out.println("[========================================================]");
            System.out.println();
            System.out.println();
        }
        Assert.assertEquals(totalValue, numberLottery, numberLottery);
    }

}
