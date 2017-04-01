package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.enums.TransactionTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private UserInvestmentService investmentService;


    private List<MessageDTO> messageDTOS;

    /**
     *
     * @param transaction
     * @param locale
     * @return
     */
    @RequestMapping(value = "/cash", method = RequestMethod.POST)
    public ResponseEntity<Object> checkoutByCash(@RequestBody Transaction transaction, Locale locale){

        //initial messageDTOS array
        messageDTOS = new ArrayList<>();

       /* SecurityToken securityToken = securityTokenSevice.checkValidToken(transaction.getSecurityToken(), messageDTOS, locale);
        if(securityToken==null)
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);*/

        Transaction localTransaction = transactionService.getOne(transaction.getId());
        if(invalidTransaction(transaction.getId(), localTransaction, messageDTOS ,locale)){
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //Minus cash of user
        User user = localTransaction.getOfUser();

        UserCash userCash = userCashService.getUserCashByUserCashId(transaction.getUserCashId());

        //checking current user cash enough to pay for transaction
        if(userCash.getValue() < localTransaction.getAmount()){
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("order.error.cash.enough", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }


        userCash = userCashService.update(transaction.getUserCashId(), -localTransaction.getAmount());

        System.out.println("Updating transaction");

        LOGGER.info("Updating transaction {} ", localTransaction);

        //status success
        TransactionStatus status = new TransactionStatus(TransactionStatusEnum.SUCCESS);
        localTransaction.setTransactionStatus(status);
        localTransaction.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));
        localTransaction.setHandleBy(user);

        //update transaction
        localTransaction = transactionService.update(localTransaction);

        LOGGER.info("Updated transaction {} ", localTransaction);

        //delete security token
       /* securityTokenSevice.remove(securityToken.getId());
        LOGGER.debug("Removed security token {} ",localTransaction.getSecurityToken());*/


        TransactionType type = null;
        if(localTransaction != null)
            type = localTransaction.getTransactionType();

        if(type != null && type.equals(new TransactionType(TransactionTypeEnum.BuyInvestmentPackage))){
            investmentPackageHandler(localTransaction, messageDTOS, locale);

        }else {
            //handle lottery
            lotteryHandler(localTransaction, messageDTOS, locale);
        }

        return new ResponseEntity<Object>(transaction, HttpStatus.OK);
    }

    /**
     *
     * @param transaction
     * @param messageDTOS
     * @param locale
     */
    private void investmentPackageHandler(Transaction transaction, List<MessageDTO> messageDTOS, Locale locale){

        InvestmentPackage investmentPackage = transaction.getInvestmentPackages().get(0);

        LocalDateTime from = LocalDateTime.now(Clock.systemDefaultZone());
        User ofUser = transaction.getOfUser();

        //add investment package to user
        UserInvestment userInvestment = userService.addInvestmentPackage(ofUser, investmentPackage.getId(), from);

        LOGGER.info("Added investment package {}", userInvestment);
        //add message success
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("order.investment.package.success", String.valueOf(transaction.getId()),locale)));

        Set<UserInvestment> userInvestments = ofUser.getUserInvestments();
        userInvestments.add(userInvestment);

        //add cash to respective investment package
        investmentPackage.getInvestmentPackageCashes().forEach(
                item -> userCashService.update(item.getCash().getId(),
                        calculateRate(item.getRatevalue(), item.getInvestmentPackage().getPrice())));

        //relogin user to see investment package just bought
        Authentication auth = new UsernamePasswordAuthenticationToken(ofUser, null, ofUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        LOGGER.info("Re login {} to load new investment package", ofUser);
    }

    private void lotteryHandler(Transaction transaction, List<MessageDTO> messageDTOS, Locale locale){
        //update status of lotteries to active
        activeLotteries(transaction.getLotteryDetails());

        //add message success
        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("order.lottery.success", String.valueOf(transaction.getId()),locale)));

        //Get current opened lottery dialing
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        //update lottery dialing has income component
        incomeComponentService.saveIncomeForLotteryDialing(lotteryDialing.getId(),transaction.getAmount());

        //save bonus for ancestor of user
        double bonusValue = bonusService.saveBonusOfUser(transaction.getOfUser(), transaction.getAmount());
    }

    /**
     * Actives all lotteries in transaction detail
     * @param lotteryDetails
     */
    private void activeLotteries(Set<LotteryDetail> lotteryDetails) {
        //update lottery
        for (LotteryDetail lotteryDetail: lotteryDetails) {
            List<Lottery> lotteries = lotteryDetail.getLotteries();
            LOGGER.info("Updating lottery {} ", lotteries);
            lotteries.forEach(lottery -> lottery.setEnabled(true));
            lotterySerivce.update(lotteries);
            LOGGER.info("Updated lottery {} ", lotteries);
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
                messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("order.success", String.valueOf(transaction.getId()),locale)));
            if(transaction.getTransactionStatus().getId()==3)
                messageDTOS.add(new MessageDTO(MessageType.WARNING,i18NService.getMessage("order.cancel", String.valueOf(transaction.getId()),locale)));
            return true;
        }
        return false;
    }


    @RequestMapping(value = "/investment-package/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<Object> checkoutInvestmentPackage(@PathVariable int transactionId, Locale locale){

        Transaction transaction = transactionService.getOne(transactionId);

        if(transaction == null){
            LOGGER.error("Transaction ID {} was not existed", transactionId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,i18NService.getMessage("transaction.error.id.not.found", String.valueOf(transactionId),locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Object>(transaction, HttpStatus.OK);
    }

    private double calculateRate(double rate, double amount){
        return (rate * amount)/100;
    }

}
