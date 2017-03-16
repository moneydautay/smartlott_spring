package com.smartlott.web.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by greenlucky on 3/16/17.
 */
@Controller
@RequestMapping(TransactionController.TRANSACTION_URL)
public class TransactionController {

    public static final String TRANSACTION_URL = "/stl_admin/";
}
