package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.OccurException;
import com.smartlott.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by greenlucky on 6/7/17.
 */
@RestController
@RequestMapping(LotteryDialingHasIncomeComponentHandler.API_LDH_INCOME_COMP)
public class LotteryDialingHasIncomeComponentHandler {
    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryDialingHasIncomeComponentHandler.class);

    public static final String API_LDH_INCOME_COMP = "/api/dialing-income-comp";


    @Autowired
    private LotteryDialingHasIncomeCompService dialingIncomeCompService;

    @Autowired
    private LotteryDialingService lotteryDialingService;

    @Autowired
    private InvestmentPackageService packageService;

    @Autowired
    private UserService userService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS;

    @GetMapping("/income-in-term")
    public ResponseEntity<Object> getIncomeInTerm() {

        LotteryDialing lotteryDialing = lotteryDialingService.getOpenedLotteryDialing(true);

        List<LotteryDialingHasIncomeComp> incomeComps = dialingIncomeCompService.getByLotteryDialingId(lotteryDialing.getId());

        return new ResponseEntity<Object>(incomeComps, HttpStatus.OK);
    }


    @GetMapping("/cal-income-member")
    public ResponseEntity<Object> divideIncomeInTerm(long termId, Locale locale) {

        messageDTOS = new ArrayList<>();

        // checking termId existed
        final LotteryDialing lotteryDialing = lotteryDialingService.getOne(termId);

        if (lotteryDialing != null) {

            LOGGER.debug("Lottery in term was not found {}", termId);

            MessageDTO msg = new MessageDTO(MessageType.SUCCESS,
                    i18NService.getMessage("admin.lottery.dialing.id.not.found.text", String.valueOf(termId), locale));
            throw new OccurException(msg);
        }

        //checking lottery dialing is closed
        if(lotteryDialing.isOpened()) {
            LOGGER.debug("Lottery in term i{} is opening", termId);

            MessageDTO msg = new MessageDTO(MessageType.SUCCESS,
                    i18NService.getMessage("admin.lottery.dialing.error.is.opening", String.valueOf(termId), locale));

            throw new OccurException(msg);
        }

        //get all investment package
        final List<IncomeComponent> incomeComponents = packageService.getAll().
                stream().filter(p -> p.getIncomeComponent() != null).map(p -> p.getIncomeComponent()).collect(Collectors.toList());

        // get all lottery dialing has income
        final List<LotteryDialingHasIncomeComp> lstIncInTerms = dialingIncomeCompService.getByIncomeComponentId(incomeComponents, termId);

        Map<User, Double> lstIncomeMembers = new HashMap<>();

        //calculate income each investment package
        lstIncInTerms.forEach(inc -> {
            List<User> lstUsers = userService.getUserByInvestmentPackage(inc.getId());
            Double income = MathUtils.round(inc.getValue()/lstUsers.size(), 4);
            lstUsers.forEach(user -> {
                lstIncomeMembers.put(user, income);
            });
        });

        return new ResponseEntity<Object>(lstIncInTerms, HttpStatus.OK);
    }


}
