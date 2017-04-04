package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Mrs Hoang on 16/12/2016.
 */
@Controller
@RequestMapping(DashBoardController.ADMIN_URL)
public class DashBoardController {

    public static final String ADMIN_URL = "/stl-admin" ;


    @RequestMapping("")
    public String dashboard(){
        return "admin/index";
    }

}
