package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserInvestment;
import com.smartlott.backend.persistence.repositories.UserInvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Service
@Transactional(readOnly = true)
public class UserInvestmentService {

    @Autowired
    private UserInvestmentRepository investmentRepository;

    @Transactional
    public UserInvestment create(UserInvestment userInvestment){
        return investmentRepository.save(userInvestment);
    }

    @Transactional
    public int disabledInvestmentPackage(long userId, LocalDateTime now, boolean status) {
        return investmentRepository.disabledInvestmentPackage(userId, now, status);
    }

    public List<UserInvestment> getUserByInvestmentPackage(long id, LocalDateTime now, boolean enabled) {
        return investmentRepository.findByInvestmentPackageIncomeComponentIdAndFromDateLessThanAndToDateGreaterThanAndEnabled(id, now, now, enabled);
    }
}
