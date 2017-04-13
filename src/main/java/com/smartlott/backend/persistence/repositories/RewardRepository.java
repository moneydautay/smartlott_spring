package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Reward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface RewardRepository extends CrudRepository<Reward, Integer>{

    List<Reward> findAll();

    Page<Reward> findAll(Pageable pageable);

    public Reward findByIncomeComponentId(long incomeComponentId);

    int deleteById(int id);

}
