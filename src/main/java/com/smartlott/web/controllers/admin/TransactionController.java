package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 3/16/17.
 */
@Controller
@RequestMapping(TransactionController.TRANSACTION_URL)
public class TransactionController {

    public static final String TRANSACTION_URL = "/stl-admin/transaction";

    public static final String TRANSACTION_VIEW_NAME = "/admin/transaction";

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
}
