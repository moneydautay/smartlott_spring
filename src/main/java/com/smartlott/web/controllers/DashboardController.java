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

    public static final String PROFILE_GENERAL_URL = "/profile";
    public static final String PROFILE_PASSWORD_URL = "/profile/password";
    public static final String PROFILE_DOC_URL = "/profile/doc";
    public static final String PROFILE_ACCOUNT_URL = "/profile/account";

    @RequestMapping("")
    public String dashboard(){
        return DASHBOARD_VIEW_NAME+"/index";
    }

    @RequestMapping(PROFILE_GENERAL_URL)
    public String profileGeneral(){
        return DASHBOARD_VIEW_NAME+"/profileGeneral";
    }

    @RequestMapping(PROFILE_PASSWORD_URL)
    public String profilePassword(){
        return DASHBOARD_VIEW_NAME+"/profilePassword";
    }

    @RequestMapping(PROFILE_DOC_URL)
    public String profileDoc(){
        return DASHBOARD_VIEW_NAME+"/profileDoc";
    }

    @RequestMapping(PROFILE_ACCOUNT_URL)
    public String profileAccount(){
        return DASHBOARD_VIEW_NAME+"/profileAccount";
    }

    @RequestMapping(PROFILE_ACCOUNT_URL+"/add")
    public String profileAddCount(){
        return DASHBOARD_VIEW_NAME+"/profileAddAccount";
    }
}
