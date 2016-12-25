package com.smartlott.utils;

import com.smartlott.backend.service.PerfectMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by greenlucky on 12/25/16.
 */
public class PerfectMoneyUtils {

    @Autowired
    private static PerfectMoneyService perfectMoneyService;

    @Value("${perfectmoney.id}")
    private static String id;

    @Value("${perfectmoney.passPhrase}")
    private static String passPhrase;

    public static String verifyAccount(String account){
        return perfectMoneyService.verifyAccount(id, passPhrase, account);
    }
}
