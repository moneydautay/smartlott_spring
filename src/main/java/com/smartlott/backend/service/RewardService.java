package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Reward;
import com.smartlott.backend.persistence.repositories.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mrs Hoang on 12/01/2017.
 */
@Service
@Transactional(readOnly = true)
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;


    @Transactional
    public Reward create(Reward reward) {
        return rewardRepository.save(reward);
    }

    public Reward getOne(int i) {
        return rewardRepository.findOne(i);
    }

    @Transactional
    public Reward update(Reward reward){
        return rewardRepository.save(reward);
    }

    public List<Reward> getAll(){
        return rewardRepository.findAll();
    }

    @Transactional
    public void delete(int id) {
        rewardRepository.delete(id);
    }

    public Reward getRewardByIncomeComponentId(long id) {
        return rewardRepository.findByIncomeComponentId(id);
    }
}
