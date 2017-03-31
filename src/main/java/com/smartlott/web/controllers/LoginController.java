package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Mrs Hoang on 20/12/2016.
 */
@Controller
public class LoginController {

    private static final String LOGIN_VIEW_NAME = "/user/login";
    private static final String RESET_PASSWORD_NAME = "/user/resetPassword";

    public static final String LOGIN_URL = "/login";
    public static final String RESET_PASSWORD_URL = "/reset-password";

    @RequestMapping(LOGIN_URL)
    public String login() {
        return LOGIN_VIEW_NAME;
    }

    @RequestMapping(RESET_PASSWORD_URL)
    public String resetPassword(@RequestParam("tk") String tk, Model model) {
        model.addAttribute("token", model);
        return RESET_PASSWORD_NAME;
    }

}
