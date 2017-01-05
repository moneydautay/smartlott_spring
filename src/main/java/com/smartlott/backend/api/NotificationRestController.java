package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Notification;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.NotificationService;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@RestController
@RequestMapping(NotificationRestController.API_NOFICATION_REST_URL)
public class NotificationRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationRestController.class);

    public static final String API_NOFICATION_REST_URL = "/api/notification";

    @Value("${notification.time.show.later}")
    private static final int TIME_SHOW_LATER = 43200;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private I18NService i18NService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getNotification(@PathVariable long userId, Locale locale){
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        List<Notification> notifications = notificationService.getByUserIdAndProcessed(userId, false, now);
        return new ResponseEntity<Object>(notifications, HttpStatus.OK);
    }

    @RequestMapping(value = "/process/{notificationId}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Object> showLaterMassage(@PathVariable long notificationId, @PathVariable int type, Locale locale){
        List<MessageDTO> messages = new ArrayList<>();
        Notification notification = notificationService.getOne(notificationId);
        if(notification == null){
            LOGGER.error("Notification id: {} was not found", notificationId);
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("notification.id.not.found", String.valueOf(notificationId), locale)));
            return new ResponseEntity<Object>(messages, HttpStatus.BAD_REQUEST);
        }
        if(type==1)
            //set time show later
            notification.setTimeShow(LocalDateTime.now(Clock.systemDefaultZone()).plusSeconds(TIME_SHOW_LATER));
        else
            notification.setProcessed(true);

        //update notification
        notificationService.update(notification);

        return new ResponseEntity<Object>(notification, HttpStatus.OK);
    }
}
