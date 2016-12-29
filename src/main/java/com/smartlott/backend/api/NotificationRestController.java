package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Notification;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@RestController
@RequestMapping(NotificationRestController.API_NOFICATION_REST_URL)
public class NotificationRestController {

    public static final String API_NOFICATION_REST_URL = "/api/notification";

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private I18NService i18NService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getNotification(@PathVariable long userId, Locale locale){
        List<Notification> notifications = notificationService.getByUserIdAndProcessed(userId, false);
        return new ResponseEntity<Object>(notifications, HttpStatus.OK);
    }
}
