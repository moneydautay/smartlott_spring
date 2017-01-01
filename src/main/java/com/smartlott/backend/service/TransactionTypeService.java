package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.TransactionType;
import com.smartlott.backend.persistence.repositories.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by greenlucky on 1/1/17.
 */
@Service
@Transactional(readOnly = true)
public class TransactionTypeService {

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Transactional
    public TransactionType createNew(TransactionType transactionType){
        return transactionTypeRepository.save(transactionType);
    }
}
