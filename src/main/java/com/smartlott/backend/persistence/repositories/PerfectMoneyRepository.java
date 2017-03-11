package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.source.PerfectMoneyDetails;
import com.smartlott.backend.persistence.domain.source.PerfectMoneyHistoryFilter;
import com.smartlott.exceptions.PerfectMoneyException;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * User: dbudunov
 * Date: 06.08.13
 * Time: 20:18
 */
@Repository
public interface PerfectMoneyRepository {

    String transferMoney(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException;

    String transferMoneyWithVerification(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException;

    String transferMoneyWithProtectionCode(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException;

    String verifyAccount(String accountId, String password, String account) throws PerfectMoneyException;

    String getTransferHistory(PerfectMoneyHistoryFilter perfectMoneyHistoryFilter) throws PerfectMoneyException;

    String getAccountBalance(String accountId, String password) throws PerfectMoneyException;

    String getExchangeRates(String currency) throws PerfectMoneyException;

	Map<String, String> transferMoneyToMap(PerfectMoneyDetails perfectMoneyDetails) throws PerfectMoneyException;
}
