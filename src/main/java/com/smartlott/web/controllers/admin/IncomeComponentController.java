package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 4/13/17.
 */
@Controller
@RequestMapping(IncomeComponentController.INCOME_COMPONENT_URL)
public class IncomeComponentController {

    public static final String INCOME_COMPONENT_URL = "/stl-admin/income-component";

    public static final String INCOME_COMPONENT_VIEW_NAME = "/admin/incomeComponent";

    @RequestMapping("/all")
    public String getAll() {
        return INCOME_COMPONENT_VIEW_NAME + "/index";
    }

    @GetMapping("/edit/{incomeComponentId}")
    public String editIncomeComponent(@PathVariable int incomeComponentId, Model model) {
        model.addAttribute("incomeComponentId", incomeComponentId);
        model.addAttribute("action", "edit");
        return INCOME_COMPONENT_VIEW_NAME + "/add";
    }

    @GetMapping("/add")
    public String createIncomeComponent(Model model) {
        model.addAttribute("action", "add");
        return INCOME_COMPONENT_VIEW_NAME + "/add";
    }

}
