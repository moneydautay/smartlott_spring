package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 4/4/17.
 */
@Controller
@RequestMapping(AdminInvestmentPackageController.INVESTMENT_PACKAGE_URL)
public class AdminInvestmentPackageController {

    public static final String INVESTMENT_PACKAGE_URL = DashBoardController.ADMIN_URL + "/investment-package";

    public static final String INVESTMENT_PACKAGE_VIEW_NAME = "admin/investmentPackage";

    @GetMapping("/all")
    public String getAll() {
        return INVESTMENT_PACKAGE_VIEW_NAME + "/index";
    }

    @GetMapping("/edit/{investmentPackageId}")
    public String getInvestmentPackage(@PathVariable int investmentPackageId, Model model) {
        model.addAttribute("investmentPackageId", investmentPackageId);
        model.addAttribute("action", "edit");
        return INVESTMENT_PACKAGE_VIEW_NAME + "/add";
    }

    @GetMapping("/add")
    public String addInvestmentPackage(Model model) {
        model.addAttribute("action", "add");
        return INVESTMENT_PACKAGE_VIEW_NAME + "/add";
    }

    @GetMapping("/delete/{investmentPackageId}")
    public String deleteInvestmentPackage(@PathVariable int investmentPackageId, Model model) {
        model.addAttribute("action", "delete");
        return INVESTMENT_PACKAGE_VIEW_NAME + "/add";
    }
}
