package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.LotteryDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greenlucky on 1/8/17.
 */
@Repository
public interface LotteryDetailRepository extends CrudRepository<LotteryDetail, Long>{


}
