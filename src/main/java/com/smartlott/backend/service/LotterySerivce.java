package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import com.smartlott.backend.persistence.domain.elastic.LotteryElastic;
import com.smartlott.backend.persistence.repositories.LotteryRepository;
import com.smartlott.backend.persistence.repositories.elasticsearch.LotteryElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 1/8/17.
 */
@Service
@Transactional(readOnly = true)
public class LotterySerivce {

    @Autowired
    private LotteryRepository lotteryRepository;

    @Autowired
    private LotteryElasticRepository lotteryElasticRepository;

    /**
     * Creates new lottery given by lottery or exception if occurs error
     * @param lottery
     * @exception  Exception
     * @return A lottery after create or exception if occurs errors
     */
    public Lottery createNewLottery(Lottery lottery) throws Exception{
        lottery = lotteryRepository.save(lottery);
        LotteryElastic lotteryElastic = new LotteryElastic(lottery);
        lotteryElasticRepository.save(lotteryElastic);
        return lottery;
    }

    /**
     * Updates lottery
     * @param lottery
     * @return A lottery after update or null if occurs errors
     */
    @Transactional
    public Lottery update(Lottery lottery) {
        lottery = lotteryRepository.save(lottery);
        LotteryElastic lotteryElastic = new LotteryElastic(lottery);
        lotteryElasticRepository.save(lotteryElastic);
        return lottery;
    }

    /**
     * Updates lottery
     * @param lotteries
     * @return A lottery after update or null if occurs errors
     */
    @Transactional
    public Iterable<Lottery> update(List<Lottery> lotteries) {
        Iterable<Lottery> localLotteries= lotteryRepository.save(lotteries);
        List<LotteryElastic> lotteryElastics = new ArrayList<>();
        localLotteries.forEach(lottery -> lotteryElastics.add(new LotteryElastic(lottery)));
        lotteryElasticRepository.save(lotteryElastics);
        return lotteries;
    }

    public List<Lottery> getAll() {
        return lotteryRepository.findAll();
    }

    public Page<Lottery> getAll(Pageable pageable) {
        return lotteryRepository.findAll(pageable);
    }

    public List<Lottery> getByLotteryDialingId(long id) {
        return lotteryRepository.findByLotteryDetailLotteryDialing_IdAndEnabled(id, true);
    }

    @Transactional
    public Iterable<Lottery> createNewLotteries(List<Lottery> lotteries) {
        return lotteryRepository.save(lotteries);
    }

    public Page<Lottery> getByUserIdAndLotteryDialingId(long userId, long lotteryDialingId, Pageable pageable) {
        return lotteryRepository.findByLotteryDetail_TransactionOfUserIdAndLotteryDetail_LotteryDialingId(userId,lotteryDialingId, pageable);
    }
}
