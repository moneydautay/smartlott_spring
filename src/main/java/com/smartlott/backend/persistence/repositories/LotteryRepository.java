package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface LotteryRepository extends CrudRepository<Lottery, Long>{

    List<Lottery> findAll();

    Page<Lottery> findAll(Pageable pageable);

    List<Lottery> findByLotteryDialing_IdAndEnabled(long id, boolean b);

    Page<Lottery> findByLotteryDetail_TransactionOfUserIdAndLotteryDialingId(long userId, long lotteryDialingId, Pageable pageable);

    List<Lottery> findByLotteryDialingId(long termId);

    @Query("select count(ll) from Lottery ll where ll.lotteryDialing.id =:termId")
    long totalLotteryInTerm(@Param("termId") long termId);
}
