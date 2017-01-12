package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncomeComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 11/01/2017.
 */
@Repository
public interface LotteryDialingHasIncomeComponentRepository extends CrudRepository<LotteryDialingHasIncomeComponent, Long>{

    Page<LotteryDialingHasIncomeComponent> findAll(Pageable pageable);

    List<LotteryDialingHasIncomeComponent> findByLotteryDialingId(long lotteryDialingId);

    Page<LotteryDialingHasIncomeComponent> findByLotteryDialingId(long lotteryDialingId, Pageable pageRequest);

    List<LotteryDialingHasIncomeComponent> findByIncomeComponentId(Long incomeComponentId);

    Page<LotteryDialingHasIncomeComponent> findByIncomeComponentId(Long incomeComponentId, Pageable pageable);
}
