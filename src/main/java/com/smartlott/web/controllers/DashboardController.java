package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 12/22/16.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    public static final String DASHBOARD_VIEW_NAME = "/dashboard/index";

    @RequestMapping("")
    public String dashboard(){
        return DASHBOARD_VIEW_NAME;
    }
}
