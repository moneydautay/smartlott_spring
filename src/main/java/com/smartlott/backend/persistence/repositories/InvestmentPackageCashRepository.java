package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackageCash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mrs Hoang on 10/02/2017.
 */
@Repository
public interface InvestmentPackageCashRepository extends CrudRepository<InvestmentPackageCash, Long> {
}
