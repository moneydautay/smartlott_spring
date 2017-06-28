package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncomeComp;
import com.smartlott.backend.persistence.domain.backend.Reward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by greenlucky on 5/14/17.
 */
@Service
public class AwardService {
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(AwardService.class);

    @Autowired
    private RewardService rewardService;


    public Map<Integer, List<Lottery>> calculateAward(List<Lottery> lotteries, List<LotteryDialingHasIncomeComp> components) {

        Map<Integer, List<Lottery>> listAwards = new HashMap<>();
        for (LotteryDialingHasIncomeComp component : components) {
            List<Lottery> listResult = new ArrayList<>();
            LOGGER.info("Dialing com: {}: val {}", component.getIncomeComponent().getName(), component.getValue());

            //Get reward give by income component
            Reward reward = rewardService.getRewardByIncomeComponentId(component.getIncomeComponent().getId());

            if (reward != null) {
                LOGGER.info("Value of jackpot]: {}", component.getValue());
                LOGGER.info("Default value of jackpot]: {}", reward.getValue());
                int numberReward = (int) (component.getValue() / reward.getValue());
                if (component.getIncomeComponent().getReward().isJackpots())
                    if (numberReward > 0)
                        numberReward = 1;

                if (numberReward > 0) {
                    LOGGER.info("Default reward: {} : {}", reward.getName(), reward.getValue());
                    LOGGER.info("Comp reward: {}", component.getValue());
                    LOGGER.info("{} : {}", reward.getName(), numberReward);

                } else
                    LOGGER.info("Reward name: {} : {}", reward.getName(), reward.getDefaultNumericReward());

                if (component.getIncomeComponent().getReward().isJackpots()) {
                    listResult = findJackpots(lotteries, numberReward);
                    lotteries.removeAll(listResult);
                } else {
                    listResult = findLotteryAwards(lotteries, reward.getCoupleNumber(), numberReward);
                    lotteries.removeAll(listResult);
                }
                LOGGER.info("Number award found: {}", listResult.size());
                LOGGER.info("---------------------------------------------------");
                LOGGER.info("Number lotteries is: {}", lotteries.size());
                //checking all list result will be deleted from lotteries
                if (lotteries.containsAll(listResult))
                    LOGGER.info("List result still exists in lotteries");

                listAwards.put(reward.getId(), listResult);
            }
        }

        return listAwards;
    }

    public List<Lottery> findJackpots(List<Lottery> lotteries, int numberReward) {
        if (numberReward == 0)
            return new ArrayList<>();
        return findLotteryAwards(lotteries, 6);
    }

    public List<Lottery> findLotteryAwards(List<Lottery> lotteries, int numberComparedCouple) {
        int index = (int) (Math.random() * lotteries.size());
        Lottery reward = lotteries.get(index);
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

}
