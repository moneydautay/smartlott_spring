package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Controller
@RequestMapping("/stl-admin/theme-management")
public class ThemeManagementController {

    private static final String THEME_URL="admin/theme";

    @RequestMapping("")
    public String themeManagement(){
        return THEME_URL+"/index";
    }


}
