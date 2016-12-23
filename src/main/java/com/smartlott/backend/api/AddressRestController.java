package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Address;
import com.smartlott.backend.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by greenlucky on 12/23/16.
 */
@RestController
@RequestMapping(AddressRestController.API_ADDRESS_URL)
public class AddressRestController {

    public static final String API_ADDRESS_URL = "/api/address";

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRestController.class);


    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAddresses(@PathVariable long userId){

        List<Address> addresses = addressService.findByUserId(userId);

        return new ResponseEntity<Object>(addresses, HttpStatus.OK);
    }
}
