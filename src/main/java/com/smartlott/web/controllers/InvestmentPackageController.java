package com.smartlott.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mrs Hoang on 15/02/2017.
 */
@Controller
@RequestMapping(InvestmentPackageController.PACKAGE_INVESTMENT_URL)
public class InvestmentPackageController
{
    public static final String PACKAGE_INVESTMENT_URL = "/investment-package";

    public static final String PACKAGE_INVESTMENT_VIEW_NAME = "/product/investmentPackage";

    public static final String PACKAGE_INVESTMENT_DETAIL_VIEW_NAME = "/product/investmentPackageDetail";

    @RequestMapping("")
    public String showInvestmentPackage(){
        return PACKAGE_INVESTMENT_VIEW_NAME;
    }

    @RequestMapping(value = "/{investmentPackageId}", method = RequestMethod.GET)
    public String showInvestmentPackage(@PathVariable int investmentPackageId, Model model){

        model.addAttribute("investmentPackageId", investmentPackageId);

        return PACKAGE_INVESTMENT_DETAIL_VIEW_NAME;
    }

}
