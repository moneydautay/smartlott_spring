package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.DivideAward;
import com.smartlott.backend.persistence.repositories.DivideAwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lamdevops on 7/3/17.
 */
@Service
@Transactional(readOnly = true)
public class DivideAwardService {

    @Autowired
    private DivideAwardRepository cashRepository;

    @Transactional
    public DivideAward create(DivideAward divideAward) {
        return cashRepository.save(divideAward);
    }

    @Transactional
    public DivideAward update(DivideAward divideAward) {
        return cashRepository.save(divideAward);
    }

    public List<DivideAward> getAll() {
        return cashRepository.findAll();
    }
}
