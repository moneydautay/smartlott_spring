package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.IncomeProcess;
import com.smartlott.backend.persistence.repositories.IncomeProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lamdevops on 6/26/17.
 */
@Service
@Transactional(readOnly = true)
public class IncomeProcessService {

    @Autowired
    private IncomeProcessRepository processRepository;


    @Transactional
    public IncomeProcess create(IncomeProcess incomeProcess) {
        return processRepository.save(incomeProcess);
    }

    @Transactional
    public IncomeProcess update(IncomeProcess incomeProcess) {
        return processRepository.save(incomeProcess);
    }

    @Transactional
    public IncomeProcess getOne(int id) {
        return processRepository.findOne(id);
    }
}
