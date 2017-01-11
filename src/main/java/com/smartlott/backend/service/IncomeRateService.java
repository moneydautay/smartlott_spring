package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.IncomeRate;
import com.smartlott.backend.persistence.repositories.IncomeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * Created by greenlucky on 1/12/17.
 * @version %I% %G%
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class IncomeRateService {

    @Autowired
    private IncomeRateRepository incomeRateRepository;

    @Transactional
    public IncomeRate create(IncomeRate incomeRate){
        return incomeRateRepository.save(incomeRate);
    }

    @Transactional
    public IncomeRate update(IncomeRate incomeRate){
        return incomeRateRepository.save(incomeRate);
    }

    public IncomeRate getOne(long id){
        return incomeRateRepository.findOne(id);
    }

    public IncomeRate getByFromDateAndToDate(LocalDateTime fromDate, LocalDateTime toDate){
        return incomeRateRepository.findByFromDateAndToDate(fromDate, toDate);
    }

    public Page<IncomeRate> getAll(Pageable pageable){
        PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC,"id"));
        return incomeRateRepository.findAll(request);
    }

    @Transactional
    public void delete(long id){
        incomeRateRepository.delete(id);
    }

}
