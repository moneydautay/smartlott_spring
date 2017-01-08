package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LotteryType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface LotteryTypeRepository extends CrudRepository<LotteryType, Integer>{

    /**
     * Finds all lottery type
     * @return A list of lottery type or null if not eixst
     * @see LotteryType
     */
    public List<LotteryType> findAll();
}
