package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Cash;
import com.smartlott.backend.persistence.repositories.CashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by greenlucky on 2/4/17.
 */
@Service
@Transactional(readOnly = true)
public class CashService {

    @Autowired
    private CashRepository cashRepository;

    @Transactional
    public Cash create(Cash cash){
        return cashRepository.save(cash);
    }

    @Transactional
    public Cash update(Cash cash){
        return cashRepository.save(cash);
    }

    public Cash getOne(int cashId){
        return cashRepository.findOne(cashId);
    }

    public List<Cash> getAll(){
        return cashRepository.findAll();
    }

    @Transactional
    public void creates(List<Cash> cashes) {
        cashRepository.save(cashes);
    }
}
