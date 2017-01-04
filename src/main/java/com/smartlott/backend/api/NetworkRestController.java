package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Network;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.NetworkService;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * Created by Mrs Hoang on 04/01/2017.
 */
@RestController
@RequestMapping(NetworkRestController.API_NETWORK_URL)
public class NetworkRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkRestController.class);

    public static final String API_NETWORK_URL = "/api/network";

    @Autowired
    private NetworkService networkService;

    @Autowired
    private I18NService i18NService;

    @RequestMapping(value = "/ofancestor/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getByAncestor(@PathVariable long userId, Pageable pageable, Locale locale){
        List<MessageDTO> messageDTOS = new ArrayList<>();

        //repairing page request
        PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
        Page<Network> networks = networkService.getByAncestor(userId, request);
        if(networks == null){
            LOGGER.error("Network of user {} was not found", userId);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,i18NService.getMessage("network.user.id.not.found", locale)));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<Object>(networks, HttpStatus.OK);
    }
}
