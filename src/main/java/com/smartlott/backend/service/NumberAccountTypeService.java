package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import com.smartlott.backend.persistence.repositories.NumberAccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by greenlucky on 12/24/16.
 */
@Service
public class NumberAccountTypeService {

    @Autowired
    private NumberAccountTypeRepository accountTypeRepository;

    /**
     * Create new account type
     * @param numberAccountType
     * @return A number account type just created
     */
    public NumberAccountType create(NumberAccountType numberAccountType){
        return accountTypeRepository.save(numberAccountType);
    }

    /**
     * Update new account type
     * @param numberAccountType
     * @return A number account type just updated
     */
    public NumberAccountType update(NumberAccountType numberAccountType){
        return accountTypeRepository.save(numberAccountType);
    }

    /**
     * Retrieve all number account type
     * @return A list of number account type exist in database
     */
    public List<NumberAccountType> getAll(){
        return accountTypeRepository.findAll();
    }

    /**
     * Retrieve a number account type
     * @param id
     * @return A number account type or null if not found
     */
    public NumberAccountType getOne(int id){
        return accountTypeRepository.findOne(id);
    }
}
