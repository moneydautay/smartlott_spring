package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.NumberAccountService;
import com.smartlott.backend.service.NumberAccountTypeService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 12/24/16.
 */
@RestController
@RequestMapping(NumberAccountTypeRestController.NUMBER_ACCOUNT_TYPE_URL)
public class NumberAccountTypeRestController {

    public static final String NUMBER_ACCOUNT_TYPE_URL = "/api/number-account-type";

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberAccountTypeRestController.class);

    @Autowired
    private NumberAccountTypeService numberAccountTypeService;


    /**
     * Retrieve all number account type
     * @return a list of number account type or null if not exist
     */
    @RequestMapping("")
    public ResponseEntity<Object> getAllNumberAccountType(){
        List<NumberAccountType> numberAccountTypes = numberAccountTypeService.getAll();
        return new ResponseEntity<Object>(numberAccountTypes, HttpStatus.OK);
    }


}
