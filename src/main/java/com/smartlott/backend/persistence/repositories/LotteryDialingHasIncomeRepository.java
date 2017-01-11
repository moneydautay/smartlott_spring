package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncome;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mrs Hoang on 11/01/2017.
 */
@Repository
public interface LotteryDialingHasIncomeRepository extends CrudRepository<LotteryDialingHasIncome, Integer>{
}
