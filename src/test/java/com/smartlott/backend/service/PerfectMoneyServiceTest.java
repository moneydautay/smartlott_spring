package com.smartlott.backend.service;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.source.PerfectMoney;
import com.smartlott.backend.persistence.domain.source.PerfectMoneyDetails;
import com.smartlott.backend.persistence.domain.source.PerfectMoneyHistoryFilter;
import com.smartlott.exceptions.PerfectMoneyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;


/**
 * Created by Mrs Hoang on 09/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class PerfectMoneyServiceTest {

    @Autowired
    private PerfectMoneyService perfectMoneyService;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(perfectMoneyService);
    }

    @Test
    public void transferMoneyWithoutVeritfy(){
        //verify payer account
        PerfectMoneyDetails details = new PerfectMoneyDetails();
        details.setAccountId("8635262");
        details.setPassPhrase("@Meo*u%1311%");
        details.setAmount(0.01);
        details.setPayerAccount("U13344806");
        details.setPayeeAccount("U9498703");
        details.setMemo("Transfer money to account U9498703");
        details.setBatch("159754395");
        details.setCode("5");
        details.setPeriod("1");
        try {

            String  result= perfectMoneyService.transferMoney(details);
            System.out.println(result);
        }catch (PerfectMoneyException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void historyFilter(){
        //verify payer account
        PerfectMoneyHistoryFilter historyFilter = new PerfectMoneyHistoryFilter();
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        historyFilter.setAccountId(Long.valueOf(8635262));
        historyFilter.setPassword("@Meo*u%1311%");
        historyFilter.setBatchFilter(159753647);
        historyFilter.setStartMonth(Integer.valueOf(now.getMonthValue()));
        historyFilter.setEndMonth(Integer.valueOf(now.getMonthValue()));
        historyFilter.setStartDay(Integer.valueOf(now.minusDays(Long.valueOf(1)).getDayOfMonth()));
        historyFilter.setEndDay(Integer.valueOf(now.plusDays(Long.valueOf(1)).getDayOfMonth()));
        historyFilter.setStartYear(Integer.valueOf(now.getYear()));
        historyFilter.setEndYear(Integer.valueOf(now.getYear()));

        try {

            String  result= perfectMoneyService.getTransferHistory(historyFilter);
            System.out.println(result);
        }catch (PerfectMoneyException ex){
            System.out.println(ex.getMessage());
        }
    }

}