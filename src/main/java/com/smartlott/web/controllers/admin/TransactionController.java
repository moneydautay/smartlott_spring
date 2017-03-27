package com.smartlott.web.controllers.admin;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.domain.source.PerfectMoney;
import com.smartlott.backend.service.ExternalHistoryAccountService;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.TransactionService;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.NumberAccountTypeEnum;
import com.smartlott.enums.TransactionStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 3/16/17.
 */
@Controller
@RequestMapping(TransactionController.TRANSACTION_URL)
public class TransactionController {

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    public static final String TRANSACTION_URL = "/stl-admin/transaction";

    public static final String TRANSACTION_VIEW_NAME = "/admin/transaction";

    @Autowired
    private I18NService i18NService;

    @Autowired
    private ExternalHistoryAccountService historyAccountService;

    @Autowired
    private TransactionService transactionService;

    private List<MessageDTO> messageDTOS;

    @GetMapping("/all")
    public String getAll() {
        return TRANSACTION_VIEW_NAME + "/index";
    }

    @GetMapping("/{transactionId}")
    public String getTransaction(@PathVariable long transactionId, Model model) {
        model.addAttribute("transactionId", transactionId);
        return TRANSACTION_VIEW_NAME + "/detail";
    }

    @GetMapping("/cancel/{transactionId}")
    public String cancelTransaction(@PathVariable long transactionId, Model model) {
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("action", "cancel");
        return TRANSACTION_VIEW_NAME + "/detail";
    }

    @GetMapping("/handle/{transactionId}")
    public String handleTransaction(@PathVariable long transactionId, Model model) {
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("action", "handle");
        return TRANSACTION_VIEW_NAME + "/detail";
    }

    @PostMapping("/handle/perfect-money")
    public String perfectMoneyHandle(@RequestParam("transactionId") long transactionId, PerfectMoney perfectMoney, Model model, Locale locale) {
        messageDTOS = new ArrayList<>();

        Transaction transaction = transactionService.getOne(transactionId);
        if (transaction == null) {
            model.addAttribute("messages", i18NService.getMessage("transaction.error.id.not.found",
                    String.valueOf(transactionId), locale));
            model.addAttribute("action", "handle");
            return TRANSACTION_VIEW_NAME + "/detail";
        }

        LOGGER.info("Updating transaction {} ", transaction);
        //status success
        TransactionStatus status = new TransactionStatus(TransactionStatusEnum.SUCCESS);
        transaction.setTransactionStatus(status);

        //update transaction
        transaction = transactionService.update(transaction);

        LOGGER.info("Updated transaction {} ", transaction);

        messageDTOS.add(new MessageDTO(MessageType.SUCCESS, i18NService.getMessage("order.lottery.success", String.valueOf(transactionId), locale)));
        model.addAttribute("messages", messageDTOS);

        //insert batch of perfect money to database
        NumberAccountType numberAccountType = new NumberAccountType(NumberAccountTypeEnum.PerfectMoney);
        ExternalHistoryAccount historyAccount = new ExternalHistoryAccount();
        historyAccount.setCreateDate(LocalDateTime.now(Clock.systemDefaultZone()));
        historyAccount.setNumberAccountType(numberAccountType);
        historyAccount.setTransaction(transaction);
        historyAccount.setExternalTransId(perfectMoney.getPAYMENT_BATCH_NUM());

        historyAccount = historyAccountService.create(historyAccount);

        LOGGER.info("Created External history account: " + historyAccount);

        model.addAttribute("transactionId", transactionId);
        model.addAttribute("action", "handle");
        return TRANSACTION_VIEW_NAME + "/detail";
    }


    @GetMapping("/error")
    public String errorTransaction(@RequestParam("transactionId") long transactionId, Model model, Locale locale) {
        messageDTOS = new ArrayList<>();
        Transaction transaction = transactionService.getOne(transactionId);


        model.addAttribute("transactionId", transactionId);
        if (transaction == null) {
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("transaction.error.id.not.found",
                    String.valueOf(transactionId), locale)));


        } else {
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("transaction.error.checkout.text",
                    String.valueOf(transactionId), locale)));
        }
        model.addAttribute("messages", messageDTOS);
        return TRANSACTION_VIEW_NAME + "/error";
    }
}
