package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Transaction;
import com.smartlott.backend.persistence.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return transactionRepository.save(transaction);
    }
}
