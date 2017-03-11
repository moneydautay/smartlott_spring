package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface NumberAccountTypeRepository extends CrudRepository<NumberAccountType, Integer>{

    /**
     * Find all number account type
     * @return A list of number account or null if not exist
     */
    List<NumberAccountType> findAll();
}
