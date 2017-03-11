package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    /**
     * Find all transaction of user
     * @param ofUserId given by user id
     * @param pageable
     * @return Page of transaction or null if not existed
     */
    Page<Transaction> findByOfUserId(long ofUserId, Pageable pageable);

    /**
     * Find transactions of user given by
     * @param userId
     * @param fromDate
     * @param toDate
     * @return A page of transaction or null if not found
     */
    Page<Transaction> findByOfUserIdAndCreatedDateBetween(long userId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
