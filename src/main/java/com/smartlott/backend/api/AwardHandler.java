package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.*;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.exceptions.OccurException;
import com.smartlott.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;
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

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private IncomeComponentService incomeComponentService;

    @Autowired
    private LottDialingIncProcessService incomeProcessService;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private UserCashService userCashService;

    @Autowired
    private DivideAwardService divideAwardService;

    private Award resultAward = new Award();

    private List<MessageDTO> messageDTOS = new ArrayList<>();

    private long totalAwards = 0;

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

        } catch (Exception ex) {
            ex.printStackTrace();
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.reward.error.calculate.lottery.reward", String.valueOf(termId), locale));
            throw new OccurException(msg);
        }

        return new ResponseEntity<>(resultAward, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/save-award")
    public ResponseEntity<Object> saveAwards(@RequestParam("termId") long termId, Locale locale) {

        LottDialingIncProcess incomeProcess = incomeProcessService.getByLotteryDialingId(termId, IncomeProcessEnum.SAVE_AWARD.getId());
        if( incomeProcess != null)
            throw new OccurException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.award.error.has.been.handed", String.valueOf(termId), locale)));

        try {

            if (resultAward.getListAwards().size() == 0) {
                MessageDTO msg = new MessageDTO(MessageType.ERROR,
                        i18NService.getMessage("admin.award.error.list.result.award.empty", String.valueOf(termId), locale));
                throw new OccurException(msg);
            }

            LotteryDialing lotteryDialing = new LotteryDialing(termId);
            resultAward.getListAwards().forEach((k, v) -> {
                Reward reward = rewardService.getOne(k);
                v.forEach(lottery -> {
                    LotteryDialingResult result = resultService.create(lotteryDialing, lottery.getBuyBy(), lottery, reward);
                    LOGGER.info("Created lottery dialing result[result id: {}, reward: {}, of user: {}, lottery id: {}, created date: {}]",
                            result.getId(), result.getReward().getId(), result.getOfUser().getUsername(),
                            result.getLottery().getId(), result.getCreateDate());
                    totalAwards++;
                });
            });

            LottDialingIncProcess incProcess = new LottDialingIncProcess();
            incProcess.setIncomeProcess(new IncomeProcess(IncomeProcessEnum.SAVE_AWARD));
            incProcess.setLotteryDialing(new LotteryDialing(termId));
            incProcess.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));

            incomeProcessService.create(incProcess);
            LOGGER.info("Create lottery dialing income process: {} ", incProcess.getId());

            LOGGER.info("Created {} dialing results", totalAwards);
            messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                    i18NService.getMessage("admin.reward.success.saving.lottery.reward",
                            new Object[]{termId, totalAwards}, locale)));

        } catch (Exception ex) {
            ex.printStackTrace();
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.reward.error.saving.lottery.reward", String.valueOf(termId), locale));
            throw new OccurException(msg);
        }

        return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/divide-award")
    public ResponseEntity<Object> divideAward(@RequestParam("termId") long termId, Locale locale) {
        LottDialingIncProcess incomeProcess = incomeProcessService.getByLotteryDialingId(termId, IncomeProcessEnum.DIVIDE_AWARD.getId());

        if( incomeProcess != null)
            throw new OccurException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.award.error.has.been.handed", String.valueOf(termId), locale)));

        try {

            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            IncomeComponent rewardAward = incomeComponentService.getOne(28);

            List<DivideAward> divideAwards = divideAwardService.getAll();

            final List<LotteryDialingResult> lstAwards = resultService.getAwardByTermId(termId);
            double totalAmount = 0;
            if (lstAwards.size() == 0) {
                throw new NotFoundException(new MessageDTO(MessageType.ERROR,
                        i18NService.getMessage("admin.lottery.dialing.result.error.not.found", String.valueOf(termId), locale)));
            }

            for (LotteryDialingResult ldr : lstAwards) {

                totalAmount += ldr.getReward().getValue();

                final double bonus = MathUtils.round((ldr.getReward().getValue() * rewardAward.getValue() / 100), 4);
                final double realAmount = ldr.getReward().getValue() - bonus;

                divideAwards.forEach(da -> {

                    //transfer money to cash account
                    UserCash userCash = userCashService.getByUserIdAndCash_Id(ldr.getOfUser().getId(), da.getCash().getId(), true);

                    LOGGER.info("UsercashId of userId {} is null", da.getCash().getId(), ldr.getOfUser().getId());

                    double localAmount = MathUtils.round((realAmount * da.getValue() / 100), 4);
                    if( userCash != null) {
                        Transaction transaction = new Transaction.Builder()
                                .setAmount(localAmount)
                                .setOfUser(ldr.getOfUser())
                                .setTransactionType(new TransactionType(TransactionTypeEnum.DivideAward))
                                .create();

                        if (transaction.getTransactionType().isAutoHandle()) {
                            transaction.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.SUCCESS));
                            transaction.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));
                            transaction.setHandleBy(currentUser);
                        }

                        transactionService.createNew(transaction);

                        LOGGER.info("Transfering award to received account of userId {}", ldr.getOfUser().getId());
                        LOGGER.info("Usercash before transfer [ userCashId: {} of userId {} with amount {} ]", userCash.getId(), ldr.getOfUser().getId(), userCash.getValue());
                        userCash.plusValue(localAmount);
                        //update cash
                        userCash = userCashService.update(userCash);
                        LOGGER.info("Transfered cash to [ userCashId: {} of userId {} with amount {} ]", userCash.getId(), ldr.getOfUser().getId(), localAmount);
                        LOGGER.info("Usercash after transfer [ userCashId: {} of userId {} with amount {} ]", userCash.getId(), ldr.getOfUser().getId(), userCash.getValue());
                    }
                });

                LOGGER.info("Divide award [dialing: {} for member: {} , reward: {}, with {}]",
                        ldr.getLotteryDialing().getId(),
                        ldr.getOfUser().getUsername(),
                        ldr.getReward().getId(),
                        ldr.getReward().getValue());

                //divide bonus to ancestor level 1
                bonusService.saveBonusAwardOfUser(ldr.getOfUser(), bonus, BonusType.Award);

                //update handled of LotteryDialingResult to true
                ldr.setHandled(true);
                ldr.setHandledDate(LocalDateTime.now(Clock.systemDefaultZone()));
                resultService.update(ldr);
            }

            //set status handle this income process this term is handle
            LottDialingIncProcess incProcess = new LottDialingIncProcess();
            incProcess.setIncomeProcess(new IncomeProcess(IncomeProcessEnum.DIVIDE_AWARD));
            incProcess.setLotteryDialing(new LotteryDialing(termId));
            incProcess.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));

            incomeProcessService.create(incProcess);
            LOGGER.info("Create lottery dialing income process: {} ", incProcess.getId());

            messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                    i18NService.getMessage("admin.reward.success.divide.lottery.reward",
                            new Object[]{lstAwards.size(), totalAmount}, locale)));

        } catch (Exception ex) {
            ex.printStackTrace();
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.reward.error.divide.lottery.reward", String.valueOf(termId), locale));
            throw new OccurException(msg);
        }
        return new ResponseEntity<>(messageDTOS, HttpStatus.OK);
    }
}
