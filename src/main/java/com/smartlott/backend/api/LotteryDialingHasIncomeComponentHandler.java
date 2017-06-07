package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.LotteryDialing;
import com.smartlott.backend.persistence.domain.backend.LotteryDialingHasIncomeComp;
import com.smartlott.backend.service.LotteryDialingHasIncomeCompService;
import com.smartlott.backend.service.LotteryDialingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by greenlucky on 6/7/17.
 */
@RestController
@RequestMapping(LotteryDialingHasIncomeComponentHandler.API_LDH_INCOME_COMP)
public class LotteryDialingHasIncomeComponentHandler {
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDialingHasIncomeComponentHandler.class);

    public static final String API_LDH_INCOME_COMP = "/api/dialing-income-comp";


    @Autowired
    private LotteryDialingHasIncomeCompService dialingIncomeCompService;

    @Autowired
    private LotteryDialingService lotteryDialingService;

    @GetMapping("/income-in-term")
    public ResponseEntity<Object> getIncomeInTerm() {

        LotteryDialing lotteryDialing = lotteryDialingService.getOpenedLotteryDialing(true);

        List<LotteryDialingHasIncomeComp> incomeComps = dialingIncomeCompService.getByLotteryDialingId(lotteryDialing.getId());

        return new ResponseEntity<Object>(incomeComps, HttpStatus.OK);
    }

}
