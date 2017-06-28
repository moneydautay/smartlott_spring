package com.smartlott.intergation;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.LotteryTypeEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Mrs Hoang on 17/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITCalculatedRewardTest {

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotteryService lotteryService;

    private static List<Lottery> lotteries = new ArrayList<>();

    @Autowired
    private LotteryDialingHasIncomeCompService componentService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private AwardService calculatedAwardService;

    @Before
    public void before() throws Exception {

    }

    @Test
    public void test40Lottery() throws Exception {

        CreateTest(40, 20);
    }

    @Test
    public void test150Lottery() throws Exception {

        CreateTest(100, 100);
    }

    @Test
    public void test2000Lottery() throws Exception {

        CreateTest(500, 100);
    }

    private String getRandom() {
        Random cop = new Random();
        String cop1 = String.valueOf(cop.nextInt(45));
        if (cop1.length() < 2)
            cop1 += "0" + cop1;
        if (cop1.length() > 2)
            cop1 = cop1.substring(0, 2);
        return cop1;
    }

    private void CreateTest(int numberLottery, double price) throws Exception {
        LotteryType lotteryType = new LotteryType(LotteryTypeEnum.TYPE1);
        //get current lottery dialing
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        Assert.assertNotNull(lotteryDialing);

        long start = System.nanoTime();
        double totalPrice = 0;
        for (int i = 0; i < numberLottery / 2; i++) {

            Lottery lottery = new Lottery();
            lottery.setCoupleOne(getRandom());
            lottery.setCoupleTwo(getRandom());
            lottery.setCoupleThree(getRandom());
            lottery.setCoupleFour(getRandom());
            lottery.setCoupleFive(getRandom());
            lottery.setCoupleSix(getRandom());
            lottery.setLotteryType(lotteryType);

            try {
                lottery = lotteryService.createNewLottery(lottery);
                lotteries.add(lottery);
                totalPrice += price;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        componentService.saveIncomeForLotteryDialing(lotteryDialing.getId(), totalPrice);


        long elapsedTime = System.nanoTime() - start;
        System.out.println("Time to add lotteries: " + elapsedTime);


        //Assert.assertEquals("Number of lottery must be equals "+numberLottery, lotteries.size(),numberLottery);

        List<LotteryDialingHasIncomeComp> components = componentService.getAll();
        List<Lottery> results = calculatedAwardService.calculateAward(lotteries, components);
        System.out.println(results.toString());
    }

    private void calculateAward(List<Lottery> lotteries, List<LotteryDialingHasIncomeComp> components) {

        double totalValue = 0;
        for (LotteryDialingHasIncomeComp component : components) {
            List<Lottery> listResult = new ArrayList<>();
            System.out.println("Dialing com: " + component.getIncomeComponent().getName() + ": val " + component.getValue());
            totalValue += component.getValue();
            //Assert.assertEquals(expectValue,component.getValue(),numberLottery);

            //Get reward give by income component
            Reward reward = rewardService.getRewardByIncomeComponentId(component.getIncomeComponent().getId());
            System.out.println();
            System.out.println();

            if (reward != null) {
                System.out.println("[========================================================]");
                System.out.println("[Value of jackpot]: " + component.getValue());
                System.out.println("[Default value of jackpot]: " + reward.getValue());
                int numberReward = (int) (component.getValue() / reward.getValue());
                if (component.getIncomeComponent().getReward().isJackpots())
                    if (numberReward > 0)
                        numberReward = 1;

                if (numberReward > 0) {
                    System.out.println("Default reward: " + reward.getName() + ": " + reward.getValue());
                    System.out.println("Comp reward: " + ": " + component.getValue());
                    System.out.println(reward.getName() + ": " + numberReward);

                } else
                    System.out.println("Reward name: " + reward.getName() + ": " + reward.getDefaultNumericReward());

                System.out.println("[========================================================]");

                if (component.getIncomeComponent().getReward().isJackpots()) {
                    listResult = findJackpots(lotteries, numberReward);
                    lotteries.removeAll(listResult);
                    System.out.println("Number award jackpots: " + listResult.size());
                } else {
                    listResult = findLotteryAwards(lotteries, reward.getCoupleNumber(), numberReward);
                    lotteries.removeAll(listResult);
                }
                System.out.println("Number award found: " + listResult.size());
                System.out.println(listResult.toString());
                System.out.println("Number lotteries is: " + lotteries.size());
                //checking all list result will be deleted from lotteries
                if (lotteries.containsAll(listResult)) {
                    System.out.println("List result still exists in lotteries");
                }
            }

            System.out.println();
            System.out.println();
        }
    }

    public List<Lottery> findJackpots(List<Lottery> lotteries, int numberReward) {
        if (numberReward == 0)
            return new ArrayList<>();
        return findLotteryAwards(lotteries, 6);
    }

    public List<Lottery> findLotteryAwards(List<Lottery> lotteries, int numberComparedCouple) {
        int index = (int) (Math.random() * lotteries.size());
        Lottery reward = lotteries.get(index);
        System.out.println(reward.printCoupleLottery(numberComparedCouple));
        List<Lottery> results = new ArrayList<>();
        results.add(reward);
        //remove jackpot from list
        lotteries.remove(index);
        //found list of lottery mark with
        for (Lottery lottery : lotteries)
            if (reward.compareTwoLotteries(lottery, numberComparedCouple))
                results.add(lottery);
        return results;
    }

    public List<Lottery> findLotteryAwards(List<Lottery> lotteries, int numberComparedCouple, int numberReward) {
        List<Lottery> lstResults = new ArrayList<>();

        //condition stop recursive
        if (lotteries.size() <= 0 || numberReward <= 0)
            return lstResults;

        lstResults = findLotteryAwards(lotteries, numberComparedCouple);
        lotteries.removeAll(lstResults);
        // find number awards equal aspect number reward
        // if number award greater than aspect number reward
        // will remove all lstResults and find again number awards
        if (lstResults.size() == numberReward)
            return lstResults;
        else if (lstResults.size() > numberReward) {
            lstResults = findLotteryAwards(lotteries, numberComparedCouple, numberReward);
        } else {
            numberReward = numberReward - lstResults.size();
            lstResults.addAll(findLotteryAwards(lotteries, numberComparedCouple, numberReward));
        }
        return lstResults;
    }

    @Test
    public void testForeach() throws Exception {
        LotteryType lotteryType = new LotteryType(LotteryTypeEnum.TYPE1);
        List<Lottery> lotteries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            Lottery lottery = new Lottery();
            lottery.setCoupleOne(getRandom());
            lottery.setCoupleTwo(getRandom());
            lottery.setCoupleThree(getRandom());
            lottery.setCoupleFour(getRandom());
            lottery.setCoupleFive(getRandom());
            lottery.setCoupleSix(getRandom());
            lottery.setLotteryType(lotteryType);

            lottery = lotteryService.createNewLottery(lottery);
            lotteries.add(lottery);
        }


        System.out.println(lotteries);

        System.out.println("All lottery after enabled");
        lotteries.forEach(lottery -> lottery.setEnabled(true));
        System.out.println(lotteries);
    }
}
