package com.smartlott.backend.api;

import com.smartlott.backend.service.RequestService;
import com.smartlott.enums.RequestStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by greenlucky on 5/17/17.
 */
@RestController
@RequestMapping(RequestHandler.API_REQUEST)
public class RequestHandler {

    public static final String API_REQUEST = "/api/request";

    @Autowired
    private RequestService requestService;

    @GetMapping(value = "/pending")
    public ResponseEntity<Object> getPendingRequest() {
        long nbPendingRequest = requestService.getNumberRequestByStatus(RequestStatusEnum.PENDING.getId());
        return new ResponseEntity<Object>(nbPendingRequest, HttpStatus.OK);
    }

}
