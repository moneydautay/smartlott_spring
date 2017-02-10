package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.UserInvestment;
import com.smartlott.backend.persistence.repositories.UserInvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Service
public class UserInvestmentService {

    @Autowired
    private UserInvestmentRepository investmentRepository;

}
