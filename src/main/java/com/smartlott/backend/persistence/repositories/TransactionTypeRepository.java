package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.TransactionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface TransactionTypeRepository extends CrudRepository<TransactionType, Long>{
}
