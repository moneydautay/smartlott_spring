package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 4/13/17.
 */
@Controller
@RequestMapping(RewardController.USER_REWARD_URL)
public class RewardController {
    public static final String USER_REWARD_URL = "/stl-admin/reward";
    public static final String USER_REWARD_VIEW_NAME = "/admin/reward";

    @RequestMapping("/all")
    public String getAll() {
        return USER_REWARD_VIEW_NAME + "/index";
    }


    @RequestMapping("/edit/{rewardId}")
    public String getReward(@PathVariable int rewardId, Model model) {
        model.addAttribute("rewardId", rewardId);
        model.addAttribute("action", "edit");
        return USER_REWARD_VIEW_NAME + "/add";
    }

    @RequestMapping("/add")
    public String getReward(Model model) {
        model.addAttribute("action", "add");
        return USER_REWARD_VIEW_NAME + "/add";
    }
}
