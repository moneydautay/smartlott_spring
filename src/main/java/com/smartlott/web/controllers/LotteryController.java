package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Mrs Hoang on 07/01/2017.
 */
@Controller
public class LotteryController {

    public static final String LOTTERY_URL = "/lottery";

    public static final String LOTTERY_BUY_URL = "/buying-lottery";

    public static final String LOTTERY_BUY_VIEW_NAME = "/lottery/buyingLottery";

    /**
     * Buying lottery controller
     * @see /lottery/buyingLottery html page
     */
    @RequestMapping(LOTTERY_BUY_URL)
    public String buyingLottery(){
        return LOTTERY_BUY_VIEW_NAME;
    }

}
