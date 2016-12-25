package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.repositories.NumberAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by greenlucky on 12/24/16.
 */
@Service
@Transactional(readOnly = true)
public class NumberAccountService {

    @Autowired
    private NumberAccountRepository accountRepository;

    /**
     * Retrieve all number account given by username
     * @param userId
     * @return A list of number account or null if not found
     */
    public List<NumberAccount> findByUsername(long userId){
        return accountRepository.findByUserId(userId);
    }

    /**
     * Create new a number account
     * @param numberAccount
     * @return A number account just created
     */
    @Transactional
    public NumberAccount create(NumberAccount numberAccount){
        return accountRepository.save(numberAccount);
    }

    /**
     * Update a number account
     * @param numberAccount
     * @return A number just updated
     */
    @Transactional
    public NumberAccount update(NumberAccount numberAccount){
        return accountRepository.save(numberAccount);
    }

    public NumberAccount findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    /**
     * Get a number account given by number account id
     * @param numberAccountId
     * @return A number account or null if not found
     */
    public NumberAccount getAccount(long numberAccountId) {
        return accountRepository.findOne(numberAccountId);
    }

    /**
     * Delete number account by number account id
     * @param numberAccount
     */
    @Transactional
    public void delete(NumberAccount numberAccount) {
        accountRepository.delete(numberAccount);
    }
}
