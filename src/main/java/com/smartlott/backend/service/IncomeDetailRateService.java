package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.IncomeDetailRate;
import com.smartlott.backend.persistence.repositories.IncomeDetailRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 12/01/2017.
 */
@Service
@Transactional
public class IncomeDetailRateService {

    @Autowired
    private IncomeDetailRateRepository detailRateRepository;

    public IncomeDetailRate create(IncomeDetailRate detailRate) {
        return detailRateRepository.save(detailRate);
    }

    public IncomeDetailRate getOne(long id) {
        return detailRateRepository.findOne(id);
    }

    public Page<IncomeDetailRate> getByIncomeRateId(long id, Pageable pageable) {

        return detailRateRepository.findByIncomeRateId(id, pageRequest(pageable));

    }

    public Page<IncomeDetailRate> getByIncomeComponentId(long id, Pageable pageable) {
        return detailRateRepository.findByIncomeComponentId(id, pageRequest(pageable));
    }

    public IncomeDetailRate update(IncomeDetailRate detailRate) {
        return detailRateRepository.save(detailRate);
    }

    public void delete(long id) {
        detailRateRepository.delete(id);
    }

    public IncomeDetailRate getByIncomeComponentIdAndIncomeRateId(long incomeComponentId, long incomeRateId) {
        return detailRateRepository.findByIncomeComponentIdAndIncomeRateId(incomeComponentId, incomeRateId);
    }

    private PageRequest pageRequest(Pageable pageable){
        return new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC,"id"));
    }

}
