package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.TransactionStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mrs Hoang on 10/01/2017.
 */
@Repository
public interface TransactionStatusRepository extends CrudRepository<TransactionStatus, Integer>{
}
