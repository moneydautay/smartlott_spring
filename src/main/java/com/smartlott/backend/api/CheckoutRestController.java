package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.TransactionStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by greenlucky on 1/26/17.
 */
@RestController
@RequestMapping(CheckoutRestController.CHECKOUT_URL)
public class CheckoutRestController {

    public static final String CHECKOUT_URL = "/api/checkout";

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutRestController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LotterySerivce lotterySerivce;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private LotteryDialingService dialingService;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private LotteryDialingHasIncomeComponentService incomeComponentService;

    @Autowired
    private SecurityTokenSevice securityTokenSevice;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCashService userCashService;


    private List<MessageDTO> messageDTOS;

    @RequestMapping(value = "/cash", method = RequestMethod.POST)
    public ResponseEntity<Object> checkoutByCash(@RequestBody Transaction transaction, Locale locale){

        //initial messageDTOS array
        messageDTOS = new ArrayList<>();

        SecurityToken securityToken = securityTokenSevice.checkValidToken(transaction.getSecurityToken(), messageDTOS, locale);
        if(securityToken==null)
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);

        Transaction localTransaction = transactionService.getOne(transaction.getId());
        if(invalidTransaction(transaction.getId(), localTransaction, messageDTOS ,locale)){
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //Minus cash of user
        User user = localTransaction.getOfUser();



        UserCash userCash = userCashService.update(transaction.getUserCashId(), -localTransaction.getAmount());

        System.out.println("Updating transaction");

        LOGGER.info("Updating transaction {} ", localTransaction);

        //status susscess
        TransactionStatus status = new TransactionStatus(TransactionStatusEnum.SUCCESS);
        localTransaction.setTransactionStatus(status);
        localTransaction.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));
        localTransaction.setHandleBy(user);

        //update transaction tion
        localTransaction = transactionService.update(localTransaction);

        LOGGER.info("Updated transaction {} ", localTransaction);

        //delete security token
        securityTokenSevice.remove(securityToken.getId());
        LOGGER.debug("Removed security token {} ",localTransaction.getSecurityToken());

        //update status of lotteries to active
        activeLotteries(localTransaction.getLotteryDetails());

        //add message success
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("order.lottery.success", String.valueOf(localTransaction.getId()),locale)));

        //Get current opened lottery dialing
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        //update lottery dialing has income component
        incomeComponentService.saveIncomeForLotteryDialing(lotteryDialing.getId(),localTransaction.getAmount());

        //save bonus for ancestor of user
        double bonusValue = bonusService.saveBonusOfUser(localTransaction.getOfUser(), localTransaction.getAmount());

        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    /**
     * Actives all lotteries in transaction detail
     * @param lotteryDetails
     */
    private void activeLotteries(Set<LotteryDetail> lotteryDetails) {
        //update lottery
        for (LotteryDetail lotteryDetail: lotteryDetails) {
            Lottery lottery = lotteryDetail.getLottery();
            LOGGER.info("Updating lottery {} ", lottery);
            lottery.setEnabled(true);
            lottery = lotterySerivce.update(lottery);
            LOGGER.info("Updated lottery {} ", lottery);
        }
    }

    private boolean invalidTransaction(Long transactionId, Transaction transaction, List<MessageDTO> messageDTOS, Locale locale) {
        if(transaction == null){
            LOGGER.error("Transaction ID {} was not existed", transactionId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,i18NService.getMessage("transaction.error.id.not.found", String.valueOf(transactionId),locale)));
            return true;
        }

        //checking successful transaction
        if(transaction.getTransactionStatus().getId() > 1){
            LOGGER.error("Transaction ID {} was handle {}", transaction.getId(),transaction.getTransactionStatus());
            if(transaction.getTransactionStatus().getId()==2)
                messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("order.lottery.success", String.valueOf(transaction.getId()),locale)));
            if(transaction.getTransactionStatus().getId()==3)
                messageDTOS.add(new MessageDTO(MessageType.WARNING,i18NService.getMessage("order.lottery.cancel", String.valueOf(transaction.getId()),locale)));
            return true;
        }
        return false;
    }



}
