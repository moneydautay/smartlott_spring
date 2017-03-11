package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.ExternalHistoryAccount;
import com.smartlott.backend.persistence.repositories.ExternalHistoryAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by greenlucky on 1/26/17.
 */
@Service
public class ExternalHistoryAccountService {

    @Autowired
    private ExternalHistoryAccountRepository historyAccountRepository;

    public ExternalHistoryAccount create(ExternalHistoryAccount historyAccount){
        return historyAccountRepository.save(historyAccount);
    }

    /**
     * Checks existing external transaction id. If exist then return true else false
     *
     * @param externalTransId
     * @return True if exist or false
     */
    public boolean existed(String externalTransId){
        ExternalHistoryAccount historyAccount = historyAccountRepository.findByExternalTransId(externalTransId);
        return historyAccount != null;
    }
}
