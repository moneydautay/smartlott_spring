package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import com.smartlott.backend.service.NumberAccountService;
import com.smartlott.backend.service.NumberAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by greenlucky on 12/24/16.
 */
@RestController
@RequestMapping(NumberAccountTypeRestController.NUMBER_ACCOUNT_TYPE_URL)
public class NumberAccountTypeRestController {

    public static final String NUMBER_ACCOUNT_TYPE_URL = "/api/number-account-type";

    @Autowired
    private NumberAccountTypeService numberAccountTypeService;

    @RequestMapping("")
    public ResponseEntity<Object> getAllNumberAccountType(){
        List<NumberAccountType> numberAccountTypes = numberAccountTypeService.getAll();
        return new ResponseEntity<Object>(numberAccountTypes, HttpStatus.OK);
    }
}
