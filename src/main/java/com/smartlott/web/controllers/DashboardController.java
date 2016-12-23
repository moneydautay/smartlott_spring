package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 12/22/16.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    public static final String DASHBOARD_VIEW_NAME = "/dashboard";

    @RequestMapping("")
    public String dashboard(){
        return DASHBOARD_VIEW_NAME+"/index";
    }

    @RequestMapping("/profile")
    public String profileGeneral(){
        return DASHBOARD_VIEW_NAME+"/profileGeneral";
    }

    @RequestMapping("/profile/password")
    public String profilePassword(){
        return DASHBOARD_VIEW_NAME+"/profilePassword";
    }

    @RequestMapping("/profile/doc")
    public String profileDoc(){
        return DASHBOARD_VIEW_NAME+"/profileDoc";
    }

    @RequestMapping("/profile/account")
    public String profileAccount(){
        return DASHBOARD_VIEW_NAME+"/profileAccount";
    }
}
