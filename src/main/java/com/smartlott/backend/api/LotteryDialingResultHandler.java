package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncomeComp;
import com.smartlott.backend.persistence.domain.backend.Reward;
import com.smartlott.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 3/13/17.
 */
@RestController
@RequestMapping(LotteryDialingResultHandler.API_LOTTERY_DIALING_RESULT_URL)
public class LotteryDialingResultHandler {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDialingResultHandler.class);
    
    public static final String API_LOTTERY_DIALING_RESULT_URL = "/api/lottery-dialing-result";

    @Autowired
    private LotteryDialingResultService dialingResultService;

    @Autowired
    private LotteryDialingService lotteryDialingService;

    @Autowired
    private LotteryDialingHasIncomeCompService componentService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private LotterySerivce lotterySerivce;

    @GetMapping("/calc-reward")
    public ResponseEntity<Object> calculatedReward(){

        LotteryDialing currentLotteryDialing = lotteryDialingService.getOpenedLotteryDialing(true);

        List<LotteryDialingHasIncomeComp> components = componentService.getByLotteryDialingId(currentLotteryDialing.getId());
        LOGGER.info("Calculating reward...");
        for (LotteryDialingHasIncomeComp component : components){

            //Get reward give by income component
            Reward reward = rewardService.getRewardByIncomeComponentId(component.getIncomeComponent().getId());

            if(reward != null) {

                int numberReward = (int) (component.getValue() / reward.getValue());

                //checking if jackpot
                if(component.getIncomeComponent().getReward().isJackpots())
                    if (numberReward > 0) numberReward = 1;

                //if numeric reward < 0 then get default reward
                if (numberReward < 0)
                    numberReward = reward.getDefaultNumericReward();

                LOGGER.info("Reward {} default value {} current value {} number of reward {}",
                        reward.getName(), reward.getValue(), component.getValue(), numberReward);
                List<Lottery> listResult = new ArrayList<>();

                //get all lottery in lottery dialing and enabled is true
                List<Lottery> lotteries = lotterySerivce.getByLotteryDialingId(currentLotteryDialing.getId());

                LOGGER.info("Number of lottery: {}", lotteries.size());

                if(component.getIncomeComponent().getReward().isJackpots()) {
                    listResult = findJackpots(lotteries, numberReward);
                    lotteries.removeAll(listResult);
                }
                else {
                    listResult = findLotteryAwards(lotteries, reward.getCoupleNumber(), numberReward);
                    lotteries.removeAll(listResult);
                }
                 LOGGER.info("Number award found: {}", listResult.size());
                //checking all list result will be deleted from lotteries
                if(lotteries.containsAll(listResult))
                    LOGGER.warn("List result still exists in lotteries");

                //save to lottery dialing result
                dialingResultService.save(currentLotteryDialing, reward, lotteries);

                LOGGER.info("Number lottery get reward {}", listResult);
                LOGGER.info("Saved lottery get reward {}", reward);
            }
        }
        LOGGER.info("Calculate reward");

        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }

    public List<Lottery> findJackpots(List<Lottery> lotteries, int numberReward){
        if(numberReward == 0)
            return new ArrayList<>();
        return findLotteryAwards(lotteries,6);
    }

    public List<Lottery> findLotteryAwards(List<Lottery> lotteries, int numberComparedCouple){

        int index = (int)(Math.random()*lotteries.size());

        Lottery reward = lotteries.get(index);

        List<Lottery> results = new ArrayList<>();

        results.add(reward);

        //remove jackpot from list
        lotteries.remove(index);

        //found list of lottery mark with
        for (Lottery lottery : lotteries)
            if(reward.compareTwoLotteries(lottery, numberComparedCouple))
                results.add(lottery);
        return results;
    }

    public List<Lottery> findLotteryAwards(List<Lottery> lotteries, int numberComparedCouple, int numberReward){
        List<Lottery> lstResults = new ArrayList<>();

        //condition stop recursive
        if(lotteries.size() <= 0 || numberReward <=0)
            return lstResults;

        lstResults = findLotteryAwards(lotteries, numberComparedCouple);
        lotteries.removeAll(lstResults);
        // find number awards equal aspect number reward
        // if number award greater than aspect number reward
        // will remove all lstResults and find again number awards
        if(lstResults.size() == numberReward)
            return lstResults;
        else if(lstResults.size() > numberReward) {
            lstResults = findLotteryAwards(lotteries, numberComparedCouple, numberReward);
        }else {
            numberReward = numberReward - lstResults.size();
            lstResults.addAll(findLotteryAwards(lotteries, numberComparedCouple, numberReward));
        }
        return lstResults;
    }

}
