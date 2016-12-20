package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Mrs Hoang on 20/12/2016.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    public static  final String REGISTER_VIEW_NAME = "/user/register";

    @RequestMapping("")
    public String register(){
        return REGISTER_VIEW_NAME;
    }
}
