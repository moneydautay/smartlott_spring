package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.ResultReward;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Repository
public interface ResultRewardRepository extends CrudRepository<ResultReward, Long>{
}
