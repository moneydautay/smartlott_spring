package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Transaction;
import com.smartlott.backend.persistence.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Created by greenlucky on 1/1/17.
 */
@Service
@Transactional(readOnly = true)
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Create new transaction by transaction
     * @param transaction given by user when trading
     * @return a transaction after created or exception when occurs error
     */
    @Transactional
    public Transaction createNew(Transaction transaction){
        transaction.setCreatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
        return transactionRepository.save(transaction);
    }

    /**
     * Retrieve all transaction of user given by user id
     * @param userId given user
     * @param pageable system
     * @return A page of transaction or null if not existed
     */
    public Page<Transaction> getAllOfUserId(long userId, Pageable pageable){
        return transactionRepository.findByOfUserId(userId, pageable);
    }

    /**
     * Retrieve all transaction of user given by
     * @param userId
     * @param fromDate
     * @param toDate
     * @param pageable
     * @return A page of transactions of null if not found
     */
    public Page<Transaction> getAllOfUserIdCreateDateBetween(long userId, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return transactionRepository.findByOfUserIdAndCreatedDateBetween(userId, fromDate, toDate, pageable);
    }

    public Transaction getOne(long transactionid) {
        return transactionRepository.findOne(transactionid);
    }

    /**
     * Updates transaction
     * @param transaction
     * @return A transaction after updated or exception if occurs errors
     */
    @Transactional
    public Transaction update(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Page<Transaction> getAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    public Page<Transaction> getCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {
        return transactionRepository.findByCreatedDateBetween(fromDate, toDate, pageable);
    }

    public Page<Transaction> getCreateDateBetweenAndStatus(LocalDateTime fromDate, LocalDateTime toDate, int statusId, Pageable pageable) {
        return transactionRepository.findByCreatedDateBetweenAndTransactionStatusId(fromDate, toDate, statusId, pageable);
    }
}
