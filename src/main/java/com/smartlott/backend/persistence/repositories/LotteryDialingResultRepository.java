package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LotteryDialingResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface LotteryDialingResultRepository extends CrudRepository<LotteryDialingResult, Long>{

    List<LotteryDialingResult> findByLotteryDialingId(long termId);
}
