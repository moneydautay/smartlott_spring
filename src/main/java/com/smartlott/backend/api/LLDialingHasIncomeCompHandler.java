package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.IncomeProcessEnum;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.enums.TransactionTypeEnum;
import com.smartlott.exceptions.OccurException;
import com.smartlott.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by greenlucky on 6/7/17.
 */
@RestController
@RequestMapping(LLDialingHasIncomeCompHandler.API_LDH_INCOME_COMP)
public class LLDialingHasIncomeCompHandler {
    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LLDialingHasIncomeCompHandler.class);

    public static final String API_LDH_INCOME_COMP = "/api/dialing-income-comp";


    @Autowired
    private LotteryDialingHasIncomeCompService dialingIncomeCompService;

    @Autowired
    private LotteryDialingService lotteryDialingService;

    @Autowired
    private InvestmentPackageService packageService;

    @Autowired
    private UserInvestmentService userInvService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LottDialingIncProcessService incomeProcessService;

    @Autowired
    private LotteryDialingHasIncomeCompService compService;

    Map<User, Double> lstIncomeMembers = new HashMap<>();

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS;

    @GetMapping("/income-in-term")
    public ResponseEntity<Object> getIncomeInTerm() {

        LotteryDialing lotteryDialing = lotteryDialingService.getOpenedLotteryDialing(true);

        List<LotteryDialingHasIncomeComp> incomeComps = dialingIncomeCompService.getByLotteryDialingId(lotteryDialing.getId());

        return new ResponseEntity<>(incomeComps, HttpStatus.OK);
    }


    @GetMapping("/cal-income-member")
    public ResponseEntity<Object> divideIncomeInTerm(@RequestParam("termId") long termId, Locale locale) {

        messageDTOS = new ArrayList<>();

        // checking termId existed
        final LotteryDialing lotteryDialing = lotteryDialingService.getOne(termId);

        if (lotteryDialing == null) {

            LOGGER.debug("Lottery in term was not found {}", termId);

            MessageDTO msg = new MessageDTO(MessageType.SUCCESS,
                    i18NService.getMessage("admin.lottery.dialing.id.not.found.text", String.valueOf(termId), locale));
            throw new OccurException(msg);
        }

        //checking lottery dialing is closed
        if (lotteryDialing.isOpened()) {
            LOGGER.debug("Lottery in term i{} is opening", termId);

            MessageDTO msg = new MessageDTO(MessageType.SUCCESS,
                    i18NService.getMessage("admin.lottery.dialing.error.is.opening", String.valueOf(termId), locale));

            throw new OccurException(msg);
        }

        //get all investment package
        final List<IncomeComponent> incomeComponents = packageService.getAll().
                stream().filter(p -> p.getIncomeComponent() != null).map(InvestmentPackage::getIncomeComponent).collect(Collectors.toList());

        // get all lottery dialing has income
        final List<LotteryDialingHasIncomeComp> lstIncInTerms = dialingIncomeCompService.getByIncomeComponentId(incomeComponents, termId);


        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());

        //calculate income each investment package
        lstIncInTerms.forEach(inc -> {
            List<UserInvestment> lstUsers = userInvService.getUserByInvestmentPackage(inc.getId(), now, true);
            Double income = MathUtils.round(inc.getValue() / lstUsers.size(), 4);

            lstUsers.forEach(ui -> lstIncomeMembers.put(ui.getUser(), income));
        });

        return new ResponseEntity<>(lstIncomeMembers, HttpStatus.OK);
    }

    @GetMapping("/do-divide-income")
    public ResponseEntity<Object> doDivideIncome(@RequestParam("termId") long termId, Locale locale) {

        LOGGER.info("List Income Members {}", lstIncomeMembers.size());

        if (lstIncomeMembers.size() == 0) {
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.lottdialing.incomecomp.error.listincome.member.empty", locale));
            throw new OccurException(msg);
        }

        if (incomeProcessService.getByLotteryDialingId(termId, IncomeProcessEnum.INCOME_PACKAGE.getId()) != null) {
            MessageDTO msg = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.income.process.error.has.been.handled", locale));
            throw new OccurException(msg);
        }

        try {

            //Income comp list
            Map<Long, Double> lstIncome = new HashMap<>();
            lstIncomeMembers.forEach((u, v) -> {

                Transaction transaction = new Transaction.Builder()
                        .setAmount(v)
                        .setOfUser(u)
                        .setHandleBy(u)
                        .setTransactionType(new TransactionType(TransactionTypeEnum.IncomeInvestment))
                        .create();
                if (transaction.getTransactionType().isAutoHandle()) {
                    transaction.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.SUCCESS));
                    transaction.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));
                }

                transactionService.createNew(transaction);

                LOGGER.info("Divide income investment for member: {} with {}", u.getId(), v);

                //set status handle this income process this term is handle

                LottDialingIncProcess incProcess = new LottDialingIncProcess();
                incProcess.setIncomeProcess(new IncomeProcess(IncomeProcessEnum.INCOME_PACKAGE));
                incProcess.setLotteryDialing(new LotteryDialing(termId));
                incProcess.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));

                incomeProcessService.create(incProcess);
                LOGGER.info("Create lottery dialing income process: {} ", incProcess.getId());

                sumDividedIncomeInvestment(lstIncome, u.getUserInvestment().getId(), v);
            });

            //decreases value of dialing income comp
            deceaseDialingIncomeComp(termId, lstIncome);

        } catch (Exception e) {
            LOGGER.debug("{}", e.getMessage());
            throw new OccurException(new MessageDTO(MessageType.ERROR, e.getMessage()));
        }

        return new ResponseEntity<>(lstIncomeMembers, HttpStatus.OK);
    }


    private void sumDividedIncomeInvestment(Map<Long, Double> lstIncome, long investmentId, double v) {
        //set divide value to lisIncome
        if (lstIncome.containsKey(investmentId)) {
            double tempValue = lstIncome.get(investmentId) + v;
            lstIncome.put(investmentId, tempValue);
        } else {
            lstIncome.put(investmentId, v);
        }
    }

    private void deceaseDialingIncomeComp(long termId, Map<Long, Double> lstIncome) {
        lstIncome.forEach((k, v) -> compService.saveIncomeForIncomeCompAndDivideIncome(termId, k, v));
    }
}
