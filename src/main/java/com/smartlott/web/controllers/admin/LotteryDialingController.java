package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mrs Hoang on 05/01/2017.
 */
@Controller
@RequestMapping(LotteryDialingController.LOTTERY_DIALING_URL)
public class LotteryDialingController {
    public static final String LOTTERY_DIALING_URL = "/stl-admin/lottery/lottery-dialing";
    public static final String LOTTERY_DIALING_VIEW_NAME = "/admin/lottery-dialing";

    @RequestMapping(value = "" ,method = RequestMethod.GET)
    public String manageLotteryDialing(){
        return LOTTERY_DIALING_VIEW_NAME+"/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String create(){
        return LOTTERY_DIALING_VIEW_NAME+"/addLottery";
    }

    @RequestMapping(value = "/close/{lotteryDialingId}", method = RequestMethod.GET)
    public String create(@PathVariable long lotteryDialingId, Model model){
        model.addAttribute("lotteryDialingId", lotteryDialingId);
        return LOTTERY_DIALING_VIEW_NAME+"/addLottery";
    }
}
