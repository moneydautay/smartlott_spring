package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 4/19/17.
 */
@Controller
@RequestMapping(LotteryManagementController.LOTTERY_URL)
public class LotteryManagementController {

    public static final String LOTTERY_URL = "/stl-admin/lottery";
    public static final String LOTTERY_VIEW_NAME = "/admin/lottery";

    @GetMapping("/all")
    public String getAll(Model model) {
        return LOTTERY_VIEW_NAME + "/index";
    }
}
