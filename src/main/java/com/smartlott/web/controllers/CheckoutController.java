package com.smartlott.web.controllers;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.domain.source.PerfectMoney;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.NumberAccountTypeEnum;
import com.smartlott.enums.TransactionStatusEnum;
import com.smartlott.enums.TransactionTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 1/8/17.
 */
@Controller
public class CheckoutController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutController.class);

    public static final String CHECKOUT_URL = "/checkout";
    public static final String CHECKOUT_RESULT_URL = "/checkout/result";

    public static final String CHECKOUT_INVESTMENT_PACKAGE_URL = CHECKOUT_URL + "/investment-package";
    public static final String CHECKOUT_LOTTERY_VIEW_NAME = "/checkout/checkoutLottery";

    public static final String CHECKOUT_BY_PERFECT_MONEY_VIEW_NAME = "/checkout/checkoutByPerfectMoney";

    public static final String CHECKOUT_BY_PERFECT_MONEY_ERROR_VIEW_NAME = "/checkout/checkoutByPerfectMoneyError";
    public static final String CHECKOUT_BY_INVESTMENT_PACKAGE_ERROR_VIEW_NAME = "/checkout/checkoutByInvestmentPackageError";

    public static final String FORM_TEST_PERFECT_MONEY_VIEW_NAME = "/checkout/formTestPerfectMoney";
    public static final String CHECKOUT_LOTTERY_BY_CASH_VIEW_NAME = "/checkout/lotteryByCash";
    public static final String CHECKOUT_INVESTMENT_PACKAGE_BY_CASH_VIEW_NAME = "/checkout/investmentPackageByCash";

    public static final String CHECKOUT_INVESTMENT_PACKAGE_VIEW_NAME = "/checkout/investmentPackage";
    public static final String CHECKOUT_INVESTMENT_PACKAGE_RESULT_VIEW_NAME = "/checkout/investmentPackageResult";

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
    private ExternalHistoryAccountService historyAccountService;

    @Autowired
    private PerfectMoneyService perfectMoneyService;

    @Autowired
    private LotteryDialingHasIncomeCompService incomeComponentService;

    @Autowired
    private SecurityTokenSevice securityTokenSevice;

    @Autowired
    private UserService userService;

    private List<MessageDTO> messageDTOS;

    @RequestMapping(CHECKOUT_URL+"/{checkoutId}")
    public String checkout(@PathVariable long checkoutId, Model model){

        model.addAttribute("checkoutId", checkoutId);

        Transaction transaction = transactionService.getOne(checkoutId);
        TransactionType type = null;
        if(transaction != null)
            type = transaction.getTransactionType();

        if(type != null && type.equals(new TransactionType(TransactionTypeEnum.BuyInvestmentPackage)))
            return CHECKOUT_INVESTMENT_PACKAGE_VIEW_NAME;

        return CHECKOUT_LOTTERY_VIEW_NAME;
    }

    @RequestMapping(value = CHECKOUT_URL+"/perfectmoney/{checkoutId}", method = RequestMethod.POST)
    public String checkoutByPerfectMoney(@PathVariable long checkoutId, PerfectMoney perfectMoney, Model model, Locale locale, ServletRequest request){
        messageDTOS = new ArrayList<>();

        Transaction transaction = transactionService.getOne(checkoutId);
        if(invalidTransaction(checkoutId, transaction, model, locale))
            return CHECKOUT_BY_PERFECT_MONEY_VIEW_NAME;

        //checks exist batch in perfect money
        LocalDateTime createDateTrans = transaction.getCreatedDate();
        LocalDate starDate = LocalDate.of(createDateTrans.getYear(), createDateTrans.getMonthValue(), createDateTrans.getDayOfMonth());
        boolean validBatch = perfectMoneyService.checkExistBatch(starDate,perfectMoney.getPAYMENT_BATCH_NUM());

        //checking batch even exist in history
        boolean existedBatch = historyAccountService.existed(perfectMoney.getPAYMENT_BATCH_NUM());
        //checking valid info: transaction id (order id), customer id
        if(transaction.getId() != perfectMoney.getORDER_NUM()
                || transaction.getOfUser().getId() != perfectMoney.getCUST_NUM()
                || transaction.getAmount() != perfectMoney.getPAYMENT_AMOUNT()
                || existedBatch || !validBatch){
            LOGGER.error("Transaction {} is not valid with return data from perfect money {}  ", transaction, perfectMoney);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,i18NService.getMessage("transaction.error.id.not.valid", String.valueOf(checkoutId),locale)));
            model.addAttribute("messages",messageDTOS);
            return CHECKOUT_BY_PERFECT_MONEY_VIEW_NAME;
        }

        System.out.println("Updating transaction");

        LOGGER.info("Updating transaction {} ", transaction);
        //status success
        TransactionStatus status = new TransactionStatus(TransactionStatusEnum.SUCCESS);
        transaction.setTransactionStatus(status);

        //update transaction
        transaction = transactionService.update(transaction);

        LOGGER.info("Updated transaction {} ", transaction);

        //update lottery
        for (LotteryDetail lotteryDetail: transaction.getLotteryDetails()) {
            List<Lottery> lotteries = lotteryDetail.getLotteries();
            LOGGER.info("Updating lottery {} ", lotteries);

            //enable all lottery
            lotteries.forEach(lottery -> lottery.setEnabled(true));

            lotterySerivce.update(lotteries);
            LOGGER.info("Updated lottery {} ", lotteries);
        }

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("order.lottery.success", String.valueOf(checkoutId),locale)));
        model.addAttribute("messages",messageDTOS);
        model.addAttribute("transaction",transaction);
        model.addAttribute("checkoutId", checkoutId);

        //Get current opened lottery dialing
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);
        //update lottery dialing has income component
        incomeComponentService.saveIncomeForLotteryDialing(lotteryDialing.getId(),transaction.getAmount());

        //save bonus for ancestor of user
        bonusService.saveBonusOfUser(transaction.getOfUser(), transaction.getAmount());

        //insert batch of perfect money to database
        NumberAccountType numberAccountType = new NumberAccountType(NumberAccountTypeEnum.PerfectMoney);
        ExternalHistoryAccount historyAccount = new ExternalHistoryAccount();
        historyAccount.setCreateDate(LocalDateTime.now(Clock.systemDefaultZone()));
        historyAccount.setNumberAccountType(numberAccountType);
        historyAccount.setTransaction(transaction);
        historyAccount.setExternalTransId(perfectMoney.getPAYMENT_BATCH_NUM());

        historyAccount = historyAccountService.create(historyAccount);

        LOGGER.info("Created External history account: "+ historyAccount);

        return CHECKOUT_BY_PERFECT_MONEY_VIEW_NAME;
    }

    private boolean invalidTransaction(Long checkoutId, Transaction transaction, Model model, Locale locale) {
        if(transaction == null){
            LOGGER.error("Transaction ID {} was not existed", checkoutId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,i18NService.getMessage("transaction.error.id.not.found", String.valueOf(checkoutId),locale)));
            model.addAttribute("messages",messageDTOS);
            return true;
        }

        //checking successful transaction
        if(transaction.getTransactionStatus().getId() > 1){
            LOGGER.error("Transaction ID {} was handle {}", checkoutId,transaction.getTransactionStatus());
            if(transaction.getTransactionStatus().getId()==2)
                messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("order.lottery.success", String.valueOf(checkoutId),locale)));
            if(transaction.getTransactionStatus().getId()==3)
                messageDTOS.add(new MessageDTO(MessageType.WARNING,i18NService.getMessage("order.lottery.cancel", String.valueOf(checkoutId),locale)));
            model.addAttribute("messages",messageDTOS);
            return true;
        }
        return false;
    }

    @RequestMapping(value = CHECKOUT_URL+"/error/{checkoutId}")
    public String checkoutPerfectMoneyError(@PathVariable long checkoutId, @RequestParam("action") String action, Model model, Locale locale){
        messageDTOS = new ArrayList<>();
        model.addAttribute("checkoutId", checkoutId);
        Transaction transaction = transactionService.getOne(checkoutId);

        TransactionType type = null;
        if(transaction != null)
            type = transaction.getTransactionType();

        if(!invalidTransaction(checkoutId, transaction, model, locale))
            messageDTOS.add(new MessageDTO(MessageType.WARNING,i18NService.getMessage("order.lottery.error.checkout", String.valueOf(checkoutId),locale)));

        model.addAttribute("messages",messageDTOS);

        if(type != null && type.equals(new TransactionType(TransactionTypeEnum.BuyInvestmentPackage)))
            return CHECKOUT_BY_INVESTMENT_PACKAGE_ERROR_VIEW_NAME;

        return CHECKOUT_BY_PERFECT_MONEY_ERROR_VIEW_NAME;
    }

    @RequestMapping(value = CHECKOUT_URL+"/perfectmoney/frmtest")
    public String perfectMoneyFrmTest(){
        return FORM_TEST_PERFECT_MONEY_VIEW_NAME;
    }

    @RequestMapping(value = CHECKOUT_URL+"/cash/{checkoutId}")
    public String checkoutByCash(@PathVariable long checkoutId, Model model){
        model.addAttribute("checkoutId", checkoutId);
        model.addAttribute("checkoutType", "cash");

        Transaction transaction = transactionService.getOne(checkoutId);

        TransactionType type = null;
        if(transaction != null)
            type = transaction.getTransactionType();

        if(type != null && type.equals(new TransactionType(TransactionTypeEnum.BuyInvestmentPackage)))
            return CHECKOUT_INVESTMENT_PACKAGE_BY_CASH_VIEW_NAME;

        return CHECKOUT_LOTTERY_BY_CASH_VIEW_NAME;
    }

    @Transactional
    @RequestMapping(value = CHECKOUT_INVESTMENT_PACKAGE_URL + "/pm/{checkoutId}", method = RequestMethod.POST)
    public String checkoutInvestmentPackage(@PathVariable long checkoutId, PerfectMoney perfectMoney, Model model, Locale locale, ServletRequest request){

        messageDTOS = new ArrayList<>();

        Transaction transaction = transactionService.getOne(checkoutId);
        if(invalidTransaction(checkoutId, transaction, model, locale))
            return CHECKOUT_BY_INVESTMENT_PACKAGE_ERROR_VIEW_NAME;

        //checks exist batch in perfect money
        LocalDateTime createDateTrans = transaction.getCreatedDate();
        LocalDate starDate = LocalDate.of(createDateTrans.getYear(), createDateTrans.getMonthValue(), createDateTrans.getDayOfMonth());
        boolean validBatch = perfectMoneyService.checkExistBatch(starDate, perfectMoney.getPAYMENT_BATCH_NUM());

        //checking batch even exist in history
        boolean existedBatch = historyAccountService.existed(perfectMoney.getPAYMENT_BATCH_NUM());

        //checking valid info: transaction id (order id), customer id
        if(transaction.getId() != perfectMoney.getORDER_NUM()
                || transaction.getOfUser().getId() != perfectMoney.getCUST_NUM()
                || transaction.getAmount() != perfectMoney.getPAYMENT_AMOUNT()
                || existedBatch || !validBatch){
            LOGGER.error("Transaction {} is not valid with return data from perfect money {}  ", transaction, perfectMoney);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,i18NService.getMessage("transaction.error.id.not.valid", String.valueOf(checkoutId),locale)));
            model.addAttribute("messages",messageDTOS);
            return CHECKOUT_BY_INVESTMENT_PACKAGE_ERROR_VIEW_NAME;
        }

        System.out.println("Updating transaction");

        LOGGER.info("Updating transaction {} ", transaction);
        //status success
        TransactionStatus status = new TransactionStatus(TransactionStatusEnum.SUCCESS);
        transaction.setTransactionStatus(status);

        //update transaction
        transaction = transactionService.update(transaction);

        LOGGER.info("Updated transaction {} ", transaction);

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS,i18NService.getMessage("order.lottery.success", String.valueOf(checkoutId),locale)));
        model.addAttribute("messages",messageDTOS);
        model.addAttribute("transaction",transaction);
        model.addAttribute("checkoutId", checkoutId);

        //insert batch of perfect money to database
        NumberAccountType numberAccountType = new NumberAccountType(NumberAccountTypeEnum.PerfectMoney);
        ExternalHistoryAccount historyAccount = new ExternalHistoryAccount();
        historyAccount.setCreateDate(LocalDateTime.now(Clock.systemDefaultZone()));
        historyAccount.setNumberAccountType(numberAccountType);
        historyAccount.setTransaction(transaction);
        historyAccount.setExternalTransId(perfectMoney.getPAYMENT_BATCH_NUM());

        historyAccount = historyAccountService.create(historyAccount);

        LOGGER.info("Created External history account: "+ historyAccount);

        //get current lottery dialing
        LotteryDialing currentLottDialing = dialingService.getOpenedLotteryDialing(true);

        InvestmentPackage investmentPackage = transaction.getInvestmentPackages().get(0);

        LocalDateTime from = currentLottDialing.getToDate();
        User ofUser = transaction.getOfUser();

        UserInvestment userInvestment = userService.addInvestmentPackage(ofUser, investmentPackage.getId(), from);

        model.addAttribute("userInvestment", userInvestment);

        return "redirect:" + CHECKOUT_RESULT_URL + "/" + transaction.getId();
    }

    @Transactional
    @RequestMapping(value = CHECKOUT_RESULT_URL + "/{checkoutId}", method = RequestMethod.GET)
    public String checkoutResult(@PathVariable long checkoutId, Model model, Locale locale){

        model.addAttribute("checkoutId", checkoutId);

        return CHECKOUT_INVESTMENT_PACKAGE_RESULT_VIEW_NAME;
    }
}
