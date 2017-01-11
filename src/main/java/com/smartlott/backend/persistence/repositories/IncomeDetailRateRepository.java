package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.IncomeDetailRate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface IncomeDetailRateRepository extends CrudRepository<IncomeDetailRate, Long>{

    public List<IncomeDetailRate> findAll(Pageable pageable);
}
