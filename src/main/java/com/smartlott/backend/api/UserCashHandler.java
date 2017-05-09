package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.UserCash;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.UserCashService;
import com.smartlott.enums.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mrs Hoang on 11/02/2017.
 */
@RestController
@RequestMapping(UserCashHandler.API_USER_CASH_URL)
public class UserCashHandler {

    public static final String API_USER_CASH_URL = "/api/user-cash";


    @Autowired
    private UserCashService userCashService;

    @Autowired
    private I18NService i18NService;

    private List<MessageDTO> messageDTOS;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserCash(@PathVariable long userId, Locale locale){

        //construct messageDTOs
        messageDTOS = new ArrayList<>();

        List<UserCash> userCashes = userCashService.getUserCashByUserId(userId);
        if(userCashes == null){
            messageDTOS.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("usercash.of.user.id.not.found",locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(userCashes, HttpStatus.OK);
    }
}
