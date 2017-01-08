package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 1/8/17.
 */
@RestController
@RequestMapping(LotteryRestController.API_LOTTERY_URL)
public class LotteryRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LotteryRestController.class);

    public static final String API_LOTTERY_URL = "/api/lottery";

    private List<MessageDTO> messageDTOS;

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private LotterySerivce lotterySerivce;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTypeService transactionTypeService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LotteryTypeService lotteryTypeService;

    @Autowired
    private LotteryDetailService lotteryDetailService;

    @Autowired
    private I18NService i18NService;

    @RequestMapping(value="", method =  RequestMethod.POST)
    public ResponseEntity<Object> addLotteries(@RequestBody Lotteries lotteries, ServletRequest request , Locale locale) throws Exception{
        messageDTOS = new ArrayList<>();

        //checking lottery dialing is opening
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        if(lotteryDialing == null){
            LOGGER.error("Lottery Dialing was closed");
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("lottery.dialing.error.closed.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        long userId = lotteries.getUserId();
        List<Lottery> localLottery= new ArrayList<>();
        Transaction transaction = null;
        if(userId != 0){
            double totalPrice = 0;
            for (Lottery lottery : lotteries.getLotteries()) {
                LotteryType lotteryType = lotteryTypeService.getOne(lottery.getLotteryType().getId());
                totalPrice += lotteryType.getPrice();
                lottery.setLotteryType(lotteryType);
                localLottery.add(lotterySerivce.createNewLottery(lottery));

                LOGGER.info("Created new lottery {}", lottery);
            }

            System.out.println(localLottery);


            User user = userService.findOne(lotteries.getUserId());

            TransactionType transactionType = transactionTypeService.getOne(2);

            transaction = new Transaction();
            transaction.setAmount(totalPrice);
            transaction.setCreatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
            transaction.setTransactionType(transactionType);
            transaction.setOfUser(user);
            transactionService.createNew(transaction);
            LOGGER.info("Created new transaction {} for user {}",transaction, user);

            String ipAddress = request.getRemoteAddr();

            //creates Lottery Details
            lotteryDetailService.createNewLotterDetails(localLottery, transaction, ipAddress);
        }

        return new ResponseEntity<Object>(transaction, HttpStatus.OK);
    }
}
