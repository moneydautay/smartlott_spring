package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import com.smartlott.backend.persistence.repositories.LotteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by greenlucky on 1/8/17.
 */
@Service
@Transactional(readOnly = true)
public class LotterySerivce {

    @Autowired
    private LotteryRepository lotteryRepository;

    /**
     * Creates new lottery given by lottery or exception if occurs error
     * @param lottery
     * @exception  Exception
     * @return A lottery after create or exception if occurs errors
     */
    public Lottery createNewLottery(Lottery lottery) throws Exception{
        return lotteryRepository.save(lottery);
    }

    /**
     * Updates lottery
     * @param lottery
     * @return A lottery after update or null if occurs errors
     */
    @Transactional
    public Lottery update(Lottery lottery) {
        return lotteryRepository.save(lottery);
    }

}
