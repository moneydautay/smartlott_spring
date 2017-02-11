package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackageCash;
import com.smartlott.backend.persistence.repositories.InvestmentPackageCashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Mrs Hoang on 10/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class InvestmentPackageCashService {

    @Autowired
    private InvestmentPackageCashRepository cashRepository;

    @Transactional
    public InvestmentPackageCash create(InvestmentPackageCash investmentPackageCash){
        return cashRepository.save(investmentPackageCash);
    }

    @Transactional
    public void creates(List<InvestmentPackageCash> investmentPackageCashes){
        cashRepository.save(investmentPackageCashes);
    }


}
