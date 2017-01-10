package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.TransactionStatus;
import com.smartlott.backend.persistence.repositories.TransactionStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mrs Hoang on 10/01/2017.
 */
@Service
public class TransactionStatusService {

    @Autowired
    private TransactionStatusRepository statusRepository;

    public TransactionStatus create(TransactionStatus status){
        return statusRepository.save(status);
    }
}
