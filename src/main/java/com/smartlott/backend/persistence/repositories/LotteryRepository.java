package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Lottery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface LotteryRepository extends CrudRepository<Lottery, Long>{

    public List<Lottery> findAll();

    Page<Lottery> findAll(Pageable pageable);

    List<Lottery> findByLotteryDetailLotteryDialing_Id(long id);

    List<Lottery> findByLotteryDetailLotteryDialing_IdAndEnabled(long id, boolean b);

}
