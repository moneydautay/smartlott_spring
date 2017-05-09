package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Address;
import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.AddressService;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 12/23/16.
 */
@RestController
@RequestMapping(AddressHandler.API_ADDRESS_URL)
public class AddressHandler {

    public static final String API_ADDRESS_URL = "/api/address";

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressHandler.class);


    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private I18NService i18NService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAddresses(@PathVariable long userId){

        List<Address> addresses = addressService.findByUserId(userId);

        return new ResponseEntity<Object>(addresses, HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<Object> saveAddress(@PathVariable long userId, @RequestBody Address address, Locale locale) {

        User user = userService.findOne(userId);

        if(user == null) {
            LOGGER.error("Id {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }

        address = addressService.createAddress(address);

        return new ResponseEntity<Object>(address, HttpStatus.OK);
    }
}
