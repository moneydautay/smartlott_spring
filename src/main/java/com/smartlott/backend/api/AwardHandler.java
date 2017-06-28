package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.exceptions.OccurException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lamdevops on 6/28/17.
 */
@RestController
@RequestMapping(AwardHandler.API_CALCULATE_REWARD_URI)
public class AwardHandler {

    public static final String API_CALCULATE_REWARD_URI = "/api/award";

    private static final Logger LOGGER = LoggerFactory.getLogger(AwardHandler.class);

    @Autowired
    private AwardService awardService;

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private LotteryDialingHasIncomeCompService incCompService;

    @Autowired
    private LotteryDialingResultService resultService;

    @Autowired
    private I18NService i18NService;

    private Award resultAward = new Award();

    @GetMapping("/finding-lottery-award")
    public ResponseEntity<Object> findLotteryAwards(@RequestParam("termId") long termId, Locale locale) {

        final List<Lottery> lstLotteryInTerm;
        final List<LotteryDialingHasIncomeComp> lstIncomeComps;
        final LotteryDialing lotteryDialing = dialingService.getOne(termId);

        // checking exist lottery dialing
        if (lotteryDialing == null) {
            LOGGER.error("Lottery dialing id {} was not found", termId);
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.lottery.dialing.id.not.found", String.valueOf(termId), locale));

            throw new NotFoundException(msg);
        }

        // checking lottery dialing was closed
        if (lotteryDialing != null) {
            LOGGER.error("Lottery dialing id {} is opening. Cannot create list awards", termId);
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.lottery.dialing.error.opening.text", String.valueOf(termId), locale));

            throw new OccurException(msg);
        }

        // get list lottery in term
        lstLotteryInTerm = lotteryService.getAllByTermId(termId);
        lstIncomeComps = incCompService.getByLotteryDialingId(termId);

        // calculate list awards
        resultAward.setListAwards(awardService.calculateAward(lstLotteryInTerm, lstIncomeComps));

        return new ResponseEntity<>(resultAward, HttpStatus.OK);
    }

    @GetMapping("/find-member-award")
    public ResponseEntity<Object> findMemberAwards(@RequestParam("termId") long termId, Locale locale) {

        

    }
}
