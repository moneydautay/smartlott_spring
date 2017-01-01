package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.WithdrawDetail;
import com.smartlott.backend.persistence.repositories.WithdrawDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by greenlucky on 1/1/17.
 */
@Service
@Transactional(readOnly = true)
public class WithdrawDetailService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawDetailService.class);

    @Autowired
    private WithdrawDetailRepository detailRepository;


    /**
     * Create new withdraw detail
     * @param withdrawDetail
     * @return Withdraw detail after create or exception if occurs errors
     */
    @Transactional
    public WithdrawDetail createNew(WithdrawDetail withdrawDetail){
        return detailRepository.save(withdrawDetail);
    }
}
