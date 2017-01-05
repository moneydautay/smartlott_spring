package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 1/1/17.
 */
@RestController
@RequestMapping(TransactionRestController.API_TRANS_URL)
public class TransactionRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRestController.class);

    public static final String API_TRANS_URL = "/api/transaction";

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WithdrawDetailService withdrawDetailService;

    @Autowired
    private SecurityTokenSevice securityTokenSevice;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private UserService userService;

    @Autowired
    private NumberAccountService accountService;

    @RequestMapping(value = "/withdraw/{numberAccountId}", method = RequestMethod.POST)
    public ResponseEntity<Object> createWithDraw(@PathVariable long numberAccountId, @RequestBody Transaction transaction, Locale locale){

        List<MessageDTO> messageDTOS = new ArrayList<>();

        //check security token
        SecurityToken localSecurityToken = securityTokenSevice.getSecurityTokenByToken(transaction.getSecurityToken());
        if(localSecurityToken == null || LocalDateTime.now(Clock.systemUTC()).isAfter(localSecurityToken.getExpiryDate())){
            LOGGER.error("Token {} is not valid", transaction.getSecurityToken());
            String message = i18NService.getMessage("security.token.invalid", transaction.getSecurityToken() , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        Long userId = transaction.getOfUser().getId();
        User localUser = userService.findOne(transaction.getOfUser().getId());
        if(localUser == null){
            LOGGER.error("User id {} not found", userId);
            String message = i18NService.getMessage("Id.user.not.found", String.valueOf(userId) , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //get number account
        NumberAccount numberAccount = accountService.getAccount(numberAccountId);

        //Checking valid amount withdraw and current amount
        DecimalFormat df = new DecimalFormat("0.0000");
        double fees = Double.valueOf(df.format((transaction.getAmount()*numberAccount.getNumberAccountType().getfeesWithdraw())/100));

        if(transaction.getAmount() > localUser.getCash()){
            LOGGER.error("Amount withdraw {} of user {} is greater than current cash", transaction.getAmount() ,localUser, localUser.getCash());
            String message = i18NService.getMessage("mycash.amount.withdraw.greater.than.current.cast", String.valueOf(transaction.getAmount()) , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }
        //add create data to transaction
        transaction.setCreatedDate(LocalDateTime.now(Clock.systemUTC()));

        //save transaction
        transaction = transactionService.createNew(transaction);

        LOGGER.info("Created transaction {}", transaction);

        //add withdraw detail
        WithdrawDetail withdrawDetail = new WithdrawDetail(transaction, numberAccount, fees);
        withdrawDetailService.createNew(withdrawDetail);
        LOGGER.info("Created withdraw detail {}", withdrawDetail);

        return new ResponseEntity<Object>(transaction, HttpStatus.OK);
    }

    @RequestMapping(value = "/ofuser/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTransactionsOfUser(@PathVariable long userId, Pageable pageable, Locale locale){
        PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        Page<Transaction> transactions = transactionService.getAllOfUserId(userId, request);
        return new ResponseEntity<Object>(transactions, HttpStatus.OK);
    }

    @RequestMapping(value = "/filterofuser/{userId}/{fromDate}/{toDate}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTransactionsOfUserByFromToDate(@PathVariable long userId, @PathVariable String fromDate,
                                                                    @PathVariable String toDate, Pageable pageable, Locale locale){
        fromDate += " 00:00:00";
        toDate += " 23:59:59";

        //Convert String to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
        LocalDateTime from = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime to = LocalDateTime.parse(toDate, formatter);
        System.out.println(from);
        System.out.println(to);
        PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        Page<Transaction> transactions = transactionService.getAllOfUserIdCreateDateBetween(userId, from, to ,request);

        return new ResponseEntity<Object>(transactions, HttpStatus.OK);
    }
}