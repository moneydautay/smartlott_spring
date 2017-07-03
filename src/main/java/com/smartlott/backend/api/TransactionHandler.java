package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.enums.TransactionTypeEnum;
import com.smartlott.exceptions.NotEnoughUserCashAmountException;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.exceptions.OccurException;
import com.smartlott.utils.FormatNumberUtils;
import com.smartlott.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by greenlucky on 1/1/17.
 */
@RestController
@RequestMapping(TransactionHandler.API_TRANS_URL)
public class TransactionHandler {

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHandler.class);

    public static final String API_TRANS_URL = "/api/transaction";

    private List<MessageDTO> messageDTOS;

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

    @Autowired
    private UserCashService userCashService;

    @RequestMapping(value = "/withdraw/{numberAccountId}", method = RequestMethod.POST)
    public ResponseEntity<Object> createWithDraw(@PathVariable long numberAccountId,
                                                 @RequestBody Transaction transaction, Locale locale) {

        messageDTOS = new ArrayList<>();

        //check security token
        SecurityToken localSecurityToken =
                securityTokenSevice.getSecurityTokenByToken(transaction.getSecurityToken());

        if (localSecurityToken == null
                || LocalDateTime.now(Clock.systemDefaultZone()).isAfter(localSecurityToken.getExpiryDate())) {

            LOGGER.error("Token {} is not valid", transaction.getSecurityToken());
            String message = i18NService.getMessage("security.token.invalid",
                    transaction.getSecurityToken(), locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        Long userId = transaction.getOfUser().getId();
        User localUser = userService.findOne(transaction.getOfUser().getId());

        if (localUser == null) {
            LOGGER.error("User id {} not found", userId);
            String message = i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //get number account
        NumberAccount numberAccount = accountService.getAccount(numberAccountId);

        //Checking valid amount withdraw and current amount
        double fees = FormatNumberUtils.formatNumber(
                (transaction.getAmount() * numberAccount.getNumberAccountType().getFeesWithdraw()) / 100);

        //get userCash detail
        UserCash userCash = userCashService.getUserCashByUserCashId(transaction.getUserCashId());

        if (transaction.getAmount() > userCash.getValue()) {
            LOGGER.error("Amount withdraw {} of user {} is greater than current cash",
                    transaction.getAmount(), localUser, userCash.getValue());

            String message = i18NService.getMessage("mycash.amount.withdraw.greater.than.current.cast",
                    String.valueOf(transaction.getAmount()), locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR, message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //minus cash of user
        double cash = userCash.getValue();
        cash -= (transaction.getAmount() + fees);
        userCash.setValue(FormatNumberUtils.formatNumber(cash));

        //update cash of user
        userCashService.update(userCash);

        //add create data to transaction
        transaction.setCreatedDate(LocalDateTime.now(Clock.systemDefaultZone()));

        //set Status for transaction
        transaction.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.PENDING));

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
    public ResponseEntity<Object> getTransactionsOfUser(@PathVariable long userId,
                                                        Pageable pageable, Locale locale) {

        PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        Page<Transaction> transactions = transactionService.getAllOfUserId(userId, request);
        return new ResponseEntity<Object>(transactions, HttpStatus.OK);
    }

    @RequestMapping(value = "/filterofuser/{userId}/{fromDate}/{toDate}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTransactionsOfUserByFromToDate(
            @PathVariable long userId, @PathVariable String fromDate,
            @PathVariable String toDate, Pageable pageable, Locale locale) {

        fromDate += " 00:00:00";
        toDate += " 23:59:59";

        //Convert String to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
        LocalDateTime from = LocalDateTime.parse(fromDate, formatter);
        LocalDateTime to = LocalDateTime.parse(toDate, formatter);

        PageRequest request = new PageRequest(pageable.getPageNumber(),
                pageable.getPageSize(), new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        Page<Transaction> transactions
                = transactionService.getAllOfUserIdCreateDateBetween(userId, from, to, request);

        return new ResponseEntity<Object>(transactions, HttpStatus.OK);
    }

    @RequestMapping(value = "/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTransactionById(@PathVariable long transactionId, Locale locale) {
        messageDTOS = new ArrayList<>();
        Transaction transaction = transactionService.getOne(transactionId);

        if (transaction == null) {
            LOGGER.error("Transaction ID {} was not existed", transactionId);
            messageDTOS.add(new MessageDTO(
                    MessageType.ERROR, i18NService.getMessage("transaction.error.id.not.found",
                    String.valueOf(transactionId), locale)));

            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //checking successful transaction
        if (transaction.getTransactionStatus().getId() > 1) {
            LOGGER.error("Transaction ID {} was handle {}", transactionId, transaction.getTransactionStatus());
            if (transaction.getTransactionStatus().getId() == 2)
                messageDTOS.add(new MessageDTO(
                        MessageType.SUCCESS, i18NService.getMessage("order.success",
                        String.valueOf(transactionId), locale)));
            if (transaction.getTransactionStatus().getId() == 3)
                messageDTOS.add(new MessageDTO(
                        MessageType.WARNING, i18NService.getMessage("order.cancel",
                        String.valueOf(transactionId), locale)));
        }

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("messages", messageDTOS);
        data.put("content", transaction);

        return new ResponseEntity<Object>(data, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTransactionDetailById(@PathVariable long transactionId, Locale locale) {
        messageDTOS = new ArrayList<>();
        Transaction transaction = transactionService.getOne(transactionId);

        if (transaction == null) {
            LOGGER.error("Transaction ID {} was not existed", transactionId);
            messageDTOS.add(new MessageDTO(
                    MessageType.ERROR, i18NService.getMessage("transaction.error.id.not.found",
                    String.valueOf(transactionId), locale)));

            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(transaction, HttpStatus.OK);
    }

    @RequestMapping(value = "/handle/{transactionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> handle(@PathVariable long transactionId,
                                         @RequestBody Transaction transaction, Locale locale) {
        messageDTOS = new ArrayList<>();
        Transaction localTrans = transactionService.getOne(transactionId);

        if (localTrans == null) {
            LOGGER.error("Transaction ID {} was not existed", transactionId);
            messageDTOS.add(new MessageDTO(
                    MessageType.ERROR, i18NService.getMessage("transaction.error.id.not.found",
                    String.valueOf(transactionId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        localTrans.setTransactionStatus(transaction.getTransactionStatus());
        localTrans.setHandleBy(transaction.getHandleBy());
        localTrans.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));

        localTrans = transactionService.update(localTrans);

        if (localTrans.getTransactionStatus().getId() == 2) {
            messageDTOS.add(new MessageDTO(
                    MessageType.SUCCESS, i18NService.getMessage("order.lottery.success",
                    String.valueOf(transactionId), locale)));
        }

        if (localTrans.getTransactionStatus().getId() == 3) {
            messageDTOS.add(new MessageDTO(
                    MessageType.SUCCESS, i18NService.getMessage("order.lottery.cancel",
                    String.valueOf(transactionId), locale)));
        }

        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    @GetMapping("/cancel/{transactionId}")
    public ResponseEntity<Object> cancel(@PathVariable long transactionId, Locale locale) {
        messageDTOS = new ArrayList<>();
        Transaction localTrans = transactionService.getOne(transactionId);

        if (localTrans == null) {
            LOGGER.error("Transaction ID {} was not existed", transactionId);
            messageDTOS.add(new MessageDTO(
                    MessageType.ERROR, i18NService.getMessage("transaction.error.id.not.found",
                    String.valueOf(transactionId), locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }
        LOGGER.info("Canceling transaction {}", localTrans);
        User handBy = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        localTrans.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.CANCEL));
        localTrans.setHandleBy(handBy);
        localTrans.setHandleDate(LocalDateTime.now(Clock.systemDefaultZone()));

        try {
            if (localTrans.getTransactionType().getId() == TransactionTypeEnum.Withdraw.getId()) {
                UserCash userCash = getTransferableCash(localTrans.getOfUser().getUserCashes());
                LOGGER.info("Cash of user before return money {}", userCash);

                //plus cash
                userCash.plusValue(localTrans.getAmount());

                //update user cash
                userCash = userCashService.update(userCash);
                LOGGER.info("Cash of user after return money {}", userCash);
            }

            transactionService.update(localTrans);
            LOGGER.info("Canceled transaction {}", localTrans);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("transaction.cancel.error.text", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
        }

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("transaction.cancel.success.text", String.valueOf(localTrans.getId()), locale)));
        return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
    }

    private UserCash getTransferableCash(Set<UserCash> userCashes) {

        for (UserCash userCash : userCashes)
            if (userCash.getCash().isTransfer())
                return userCash;

        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(@RequestParam(name = "fromDate", required = false) String fromDate,
                                         @RequestParam(name = "toDate", required = false) String toDate,
                                         @RequestParam(name = "status", required = false, defaultValue = "0") Integer status,
                                         Pageable pageable, Locale locale) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                new Sort(Sort.Direction.DESC, "id"));
        Page<Transaction> transactions = null;
        if (fromDate != null) {

            fromDate += " 00:00:00";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy kk:mm:ss");
            LocalDateTime from = LocalDateTime.parse(fromDate, formatter);
            LocalDateTime to = LocalDateTime.now(Clock.systemDefaultZone());;

            if (toDate != null) {
                toDate += " 23:59:00";
                to = LocalDateTime.parse(toDate, formatter);
            }

            if (status != 0)
                transactions = transactionService.getCreateDateBetweenAndStatus(from, to, status, pageRequest);
            else
                transactions = transactionService.getCreateDateBetween(from, to, pageRequest);
        } else {
            transactions = transactionService.getAll(pageRequest);
        }

        return new ResponseEntity<Object>(transactions, HttpStatus.OK);
    }

    @GetMapping("/withdraw-detail/{transactionId}")
    public ResponseEntity<Object> getWithdrawDetail(@PathVariable long transactionId, Locale locale) {

        WithdrawDetail wdd = withdrawDetailService.getByTransactionId(transactionId);

        if (wdd == null) {
            messageDTOS.add(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("transaction.error.withdraw.not.found.text",
                            String.valueOf(transactionId), locale)));
        }

        return new ResponseEntity<Object>(wdd, HttpStatus.OK);
    }

    /**
     * Transfer cash to another user given by fromUser, toUser, amount, userCashId.
     *
     * @param fromUser
     * @param toUser
     * @param amount
     * @param cashId
     * @param locale
     * @return A map<String, Object> with contains a message successful transfer cash and detail of transaction
     */
    @GetMapping("/transfer")
    public ResponseEntity<Object> transferCash(@RequestParam("fromUser") String fromUser,
                                               @RequestParam("toUser") String toUser,
                                               @RequestParam("amount") double amount,
                                               @RequestParam("cashId") long cashId, Locale locale) {

        User localFromUser = userService.findByUsername(fromUser);
        //check from use exist in system
        if(null == localFromUser) {
            throw  new NotFoundException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("user.cash.transfer.error.not.found.from.user.text", fromUser, locale)));
        }

        User localToUser = userService.findByUsername(toUser);
        //check from use exist in system
        if(null == localToUser) {
            throw  new NotFoundException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("user.cash.transfer.error.not.found.to.user.text", toUser, locale)));
        }

        UserCash cash = userCashService.getUserCashByUserCashId(cashId);
        if(cash.getValue() < amount) {
            throw new NotEnoughUserCashAmountException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("user.cash.transfer.error.not.enough.amount.text", locale)));
        }

        //checking transferable of cash
        if(!cash.getCash().isTransfer()) {
            throw new OccurException(new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("user.cash.transfer.error.forbidden.account.text", locale)));
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCreatedDate(LocalDateTime.now(Clock.systemDefaultZone()));
        transaction.setOfUser(localFromUser);
        transaction.setTransactionStatus(new TransactionStatus(TransactionStatusEnum.SUCCESS));
        transaction.setTransactionType(new TransactionType(TransactionTypeEnum.SendCash));

        //set receiver user
        transaction.setToUsers(new HashSet<User>(Arrays.asList(localToUser)));

        transaction = transactionService.createNew(transaction);

        //minus cash of transfer
        userCashService.update(localFromUser.getId(), cash.getCash().getId(), -amount);

        //plus cash of receiver
        userCashService.update(localToUser.getId(), cash.getCash().getId(), amount);

        Map<String, Object> response = ResponseUtil.Response(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("user.cash.transfer.success.text", new Object[]{amount, toUser}, locale))
                ,transaction);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
