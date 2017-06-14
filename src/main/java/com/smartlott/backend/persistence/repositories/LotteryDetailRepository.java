package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LotteryDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by greenlucky on 1/8/17.
 */
@Repository
public interface LotteryDetailRepository extends CrudRepository<LotteryDetail, Long>{


    Page<LotteryDetail> findByTransactionOfUserId(long transactionOfUserId, Pageable pageable);

    List<LotteryDetail> findByLotteryDialingId(long termId);
}
