package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface LotteryDialingRepository extends CrudRepository<LotteryDialing, Long>{

    /**
     * find all lottery dialing
     *  @param pageable
     * @return A list of lottery dialing or null if not eixst
     */
    public Page<LotteryDialing> findAll(Pageable pageable);

    /**
     * Find lottery dialing is opening
     * @param opened
     * @return A lottery dialing is opening or null if not found
     */
    LotteryDialing findByOpened(boolean opened);

    @Query("select dl from LotteryDialing dl where dl.id = (select max(d.id) from LotteryDialing d)")
    LotteryDialing findByLastDialingLottery();
}
