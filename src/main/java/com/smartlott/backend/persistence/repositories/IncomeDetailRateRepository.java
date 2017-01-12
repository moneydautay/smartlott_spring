package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.IncomeDetailRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface IncomeDetailRateRepository extends CrudRepository<IncomeDetailRate, Long>{

    public Page<IncomeDetailRate> findAll(Pageable pageable);

    Page<IncomeDetailRate> findByIncomeRateId(long id, Pageable pageable);

    Page<IncomeDetailRate> findByIncomeComponentId(long id, Pageable pageable);

    IncomeDetailRate findByIncomeComponentIdAndIncomeRateId(long incomeComponentId, long incomeRateId);
}
