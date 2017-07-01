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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    private RewardService rewardService;

    private Award resultAward = new Award();

    private List<MessageDTO> messageDTOS = new ArrayList<>();

    @GetMapping("/finding-lottery-award")
    public ResponseEntity<Object> findLotteryAwards(@RequestParam("termId") long termId, Locale locale) {
        try {
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
            if (lotteryDialing.isOpened()) {
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
            LOGGER.info("Calculated {} dialing results", resultAward.getListAwards().size());

        }catch (Exception ex) {
            ex.printStackTrace();
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("s", String.valueOf(termId), locale));
            throw new OccurException(msg);
        }

        return new ResponseEntity<>(resultAward, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/save-award")
    public ResponseEntity<Object> saveAwards(@RequestParam("termId") long termId, Locale locale) {
        try {
            LotteryDialing lotteryDialing = new LotteryDialing(termId);
            resultAward.getListAwards().forEach((k, v) -> {
                Reward reward = rewardService.getOne(k);
                v.forEach(lottery -> {
                    LotteryDialingResult result =  resultService.create(lotteryDialing, lottery.getBuyBy(), lottery, reward);
                    LOGGER.info("Created lottery dialing result[result id: {}, reward: {}, of user: {}, lottery id: {}, created date: {}]",
                            result.getId(), result.getReward().getId(), result.getOfUser().getUsername(),
                            result.getLottery().getId(), result.getCreateDate());
                });
            });

            LOGGER.info("Created {} dialing results", resultAward.getListAwards().size());
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.reward.success.saving.lottery.reward",
                            new Object[]{termId, resultAward.getListAwards().size()}, locale)));

        }catch (Exception ex) {
            ex.printStackTrace();
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.reward.error.saving.lottery.reward", String.valueOf(termId), locale));
            throw new OccurException(msg);
        }

        return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
    }

}
