package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.WithdrawDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greenlucky on 1/1/17.
 */
@Repository
public interface WithdrawDetailRepository extends CrudRepository<WithdrawDetail, Long>{

}
