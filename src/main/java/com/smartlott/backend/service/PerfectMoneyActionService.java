package com.smartlott.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by greenlucky on 12/25/16.
 */
@Service
public class PerfectMoneyActionService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PerfectMoneyActionService.class);

    @Autowired
    private PerfectMoneyService perfectMoneyService;

    @Value("${perfectmoney.id}")
    private static String id;

    @Value("${perfectmoney.passPhrase}")
    private static String passPhrase;

    public String verifyAccount(String account){
        return perfectMoneyService.verifyAccount(id, passPhrase, account);
    }

}
