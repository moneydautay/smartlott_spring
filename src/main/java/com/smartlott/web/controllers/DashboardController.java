package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by greenlucky on 12/22/16.
 */
@Controller
public class DashboardController {

    public static final String DASHBOARD_VIEW_NAME = "/dashboard";
    public static final String DASHBOARD_URL = "/dashboard";
    public static final String PROFILE_GENERAL_URL = "/dashboard/profile";
    public static final String PROFILE_PASSWORD_URL = "/dashboard/profile/password";
    public static final String PROFILE_DOC_URL = "/dashboard/profile/doc";
    public static final String PROFILE_ACCOUNT_URL = "/dashboard/profile/account";
    public static final String MY_CASH_ACCOUNT_URL = "/dashboard/mycash";

    @RequestMapping(DASHBOARD_URL)
    public String dashboard(){
        return DASHBOARD_VIEW_NAME+"/index";
    }

    /**
     * THIS IS PROFILE SECTION
     */

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

    @RequestMapping(value = PROFILE_ACCOUNT_URL+"/add", method = RequestMethod.GET)
    public String profileAddCount(){
        return DASHBOARD_VIEW_NAME+"/profileAddAccount";
    }

    @RequestMapping(value = PROFILE_ACCOUNT_URL+"/edit/{accountNumberId}", method = RequestMethod.GET)
    public String profileAddCount(@PathVariable long accountNumberId, Model model){
        model.addAttribute("accountNumberId");
        return DASHBOARD_VIEW_NAME+"/profileAddAccount";
    }

    @RequestMapping(value = PROFILE_ACCOUNT_URL+"/delete/{accountNumberId}", method = RequestMethod.GET)
    public String profileDeleteAccount(@PathVariable long accountNumberId, Model model){
        model.addAttribute("accountNumberId");
        return DASHBOARD_VIEW_NAME+"/profileAddAccount";
    }

    /**
     * THIS IS MY CASH SECTION
     */
    @RequestMapping(value = MY_CASH_ACCOUNT_URL, method = RequestMethod.GET)
    public String myCash(){
        return DASHBOARD_VIEW_NAME+"/myCash";
    }


}
