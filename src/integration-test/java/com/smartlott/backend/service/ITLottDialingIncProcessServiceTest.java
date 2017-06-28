package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.IncomeProcess;
import com.smartlott.backend.persistence.domain.backend.LottDialingIncProcess;
import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import com.smartlott.enums.IncomeProcessEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by lamdevops on 6/26/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITLottDialingIncProcessServiceTest {

    @Autowired
    private LottDialingIncProcessService incomeProcessService;

    @Test
    public void create() throws Exception {
        LotteryDialing lotteryDialing =  new LotteryDialing();
        lotteryDialing.setId(1);

        LottDialingIncProcess incProcess = new LottDialingIncProcess();
        incProcess.setIncomeProcess(new IncomeProcess(IncomeProcessEnum.INCOME_PACKAGE));
        incProcess.setLotteryDialing(lotteryDialing);
        incProcess.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));
        incomeProcessService.create(incProcess);
        System.out.println(incProcess);
    }

}