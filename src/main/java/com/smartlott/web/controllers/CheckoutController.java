package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 1/8/17.
 */
@Controller
public class CheckoutController {

    public static final String CHECKOUT_URL = "/checkout";
    public static final String CHECKOUT_VIEW_NAME = "/checkout/checkoutLottery";

    @RequestMapping(CHECKOUT_URL+"/{checkoutId}")
    public String checkout(@PathVariable long checkoutId, Model model){
        model.addAttribute("checkoutId", checkoutId);
        return CHECKOUT_VIEW_NAME;
    }
}
