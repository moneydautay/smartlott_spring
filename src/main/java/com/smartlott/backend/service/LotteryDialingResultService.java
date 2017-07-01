package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.repositories.LotteryDialingResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 3/13/17.
 */
@Service
@Transactional(readOnly = true)
public class LotteryDialingResultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDialingResultService.class);

    @Autowired
    private LotteryDialingResultRepository dialingResultRepository;

    @Transactional
    public LotteryDialingResult create(LotteryDialing lotteryDialing, User user,
                                       Lottery lottery, Reward reward){
        LotteryDialingResult lotteryDialingResult = new LotteryDialingResult();
        lotteryDialingResult.setReward(reward);
        lotteryDialingResult.setCreateDate(LocalDateTime.now(Clock.systemDefaultZone()));
        lotteryDialingResult.setLottery(lottery);
        lotteryDialingResult.setLotteryDialing(lotteryDialing);
        lotteryDialingResult.setOfUser(user);

        return dialingResultRepository.save(lotteryDialingResult);
    }


    @Transactional
    public void save(LotteryDialing lotteryDialing, Reward reward, List<Lottery> lotteries) {
        List<LotteryDialingResult> lotteryDialingResults = new ArrayList<>();
        for (Lottery lottery: lotteries) {
            lotteryDialingResults.add(createLottery(lotteryDialing, lottery, reward));
        }

        dialingResultRepository.save(lotteryDialingResults);
    }

    public LotteryDialingResult createLottery(LotteryDialing lotteryDialing,
                                              Lottery lottery, Reward reward){

        User user = lottery.getLotteryDetail().getTransaction().getOfUser();
        LotteryDialingResult lotteryDialingResult = new LotteryDialingResult();
        lotteryDialingResult.setReward(reward);
        lotteryDialingResult.setCreateDate(LocalDateTime.now(Clock.systemDefaultZone()));
        lotteryDialingResult.setLottery(lottery);
        lotteryDialingResult.setLotteryDialing(lotteryDialing);
        lotteryDialingResult.setOfUser(user);

        return lotteryDialingResult;
    }
}
