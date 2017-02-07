package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import com.smartlott.backend.persistence.domain.backend.SaleOff;
import com.smartlott.backend.persistence.repositories.SaleOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class SaleOffService {

    @Autowired
    private SaleOffRepository saleOffRepository;

    @Transactional
    public SaleOff create(LocalDateTime from , LocalDateTime to, List<InvestmentPackage> investmentPackages){
        SaleOff saleOff = new SaleOff();
        saleOff.setFrom(from);
        saleOff.setTo(to);
        saleOff.setInvestmentPackages(investmentPackages);
        saleOff.setEnabled(true);

        return saleOffRepository.save(saleOff);
    }

    public SaleOff update(LocalDateTime from , LocalDateTime to, List<InvestmentPackage> investmentPackages){

    }
}
