package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by greenlucky on 4/21/17.
 */
@Controller
@RequestMapping(UserManagementController.USER_MANAGEMENT_URL)
public class UserManagementController {

    public static final String USER_MANAGEMENT_URL = "/stl-admin/user";
    public static final String USER_MANAGEMENT_VIEW_NAME = "/admin/user";

    @GetMapping("/all")
    public String getAll() {
        return USER_MANAGEMENT_VIEW_NAME + "/index";
    }

    @GetMapping("/edit/{userId}")
    public String getUser(@PathVariable long userId,
                          @RequestParam(value = "tab", required = true, defaultValue = "general") String tab,
                          Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("action", "edit");
        model.addAttribute("tab", tab);

        switch (tab) {
            case "password":
                return USER_MANAGEMENT_VIEW_NAME + "/detailPassword";
            case "doc":
                return USER_MANAGEMENT_VIEW_NAME + "/detailDoc";
            case "role":
                return USER_MANAGEMENT_VIEW_NAME + "/detailRole";
        }

        return USER_MANAGEMENT_VIEW_NAME + "/detailGeneral";
    }


    @GetMapping("/add")
    public String getUserAdd( Model model) {
        model.addAttribute("action", "add");
        return USER_MANAGEMENT_VIEW_NAME + "/detailGeneral";
    }
}
