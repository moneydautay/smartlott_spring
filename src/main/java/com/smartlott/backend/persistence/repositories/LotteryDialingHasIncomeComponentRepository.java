package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.IncomeComponent;
import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncomeComp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 11/01/2017.
 */
@Repository
public interface LotteryDialingHasIncomeComponentRepository extends CrudRepository<LotteryDialingHasIncomeComp, Long>{

    List<LotteryDialingHasIncomeComp> findAll();

    Page<LotteryDialingHasIncomeComp> findAll(Pageable pageable);

    List<LotteryDialingHasIncomeComp> findByLotteryDialingId(long lotteryDialingId);

    Page<LotteryDialingHasIncomeComp> findByLotteryDialingId(long lotteryDialingId, Pageable pageRequest);

    Page<LotteryDialingHasIncomeComp> findByIncomeComponentId(Long incomeComponentId, Pageable pageable);

    List<LotteryDialingHasIncomeComp> findByLotteryDialingIdAndIncomeComponentIn(long dialingId, List<IncomeComponent> incomeComponentIds);
}
