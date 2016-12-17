package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.IncomeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface IncomeRateRepository extends CrudRepository<IncomeRate, Long>{

    public List<IncomeRate> findAll();
}
