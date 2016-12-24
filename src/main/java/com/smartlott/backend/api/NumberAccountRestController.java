package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.service.NumberAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 12/24/16.
 */
@RestController
@RequestMapping(NumberAccountRestController.NUMBER_ACCOUNT_URL)
public class NumberAccountRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberAccountRestController.class);
    public static final String NUMBER_ACCOUNT_URL = "/api/number-account";


    @Autowired
    private NumberAccountService numberAccountService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAccounts(@PathVariable long userId){

        List<NumberAccount> numberAccounts = numberAccountService.findByUsername(userId);

        return new ResponseEntity<Object>(numberAccounts, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid NumberAccount numberAccount, Locale locale){
        LOGGER.info("Creating number account {}", numberAccount);

        numberAccount = numberAccountService.create(numberAccount);

        return new ResponseEntity<Object>(numberAccount, HttpStatus.OK);
    }
}
