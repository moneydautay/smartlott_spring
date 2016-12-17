package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface NumberAccountTypeRepository extends CrudRepository<NumberAccountType, Integer>{
}
