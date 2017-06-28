package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.LottDialingIncProcess;
import com.smartlott.backend.persistence.repositories.LottDialingIncProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lamdevops on 6/26/17.
 */
@Service
@Transactional(readOnly = true)
public class LottDialingIncProcessService {

    @Autowired
    private LottDialingIncProcessRepository incProcessRepository;

    @Transactional
    public LottDialingIncProcess create(LottDialingIncProcess dialingIncProcess) {
        return incProcessRepository.save(dialingIncProcess);
    }

    public LottDialingIncProcess getOne(long id) {
        return incProcessRepository.findOne(id);
    }

    public LottDialingIncProcess getByLotteryDialingId(long dialingId, int incomeProcessId) {
        return incProcessRepository.findByLotteryDialingIdAndIncomeProcessId(dialingId, incomeProcessId);
    }
}
