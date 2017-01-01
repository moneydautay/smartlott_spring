package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Notification;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@Service
@Transactional(readOnly = true)
public class NotificationService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
    
    @Autowired
    private NotificationRepository notificationRepository;


    public List<Notification> getAll(){
        return notificationRepository.findAll();
    }

    public Notification getByUserIdAndUserUrlAndProcessed(long userId, String url, boolean processed){
        return notificationRepository.findByUserIdAndNotificationTypeUrlAndProcessed(userId, url, processed);
    }

    public Notification getByUserIdAndUserApiUrlAndProcessed(long userId, String apiUrl, boolean processed){
        return notificationRepository.findByUserIdAndNotificationTypeApiUrlAndProcessed(userId, apiUrl, processed);
    }

    @Transactional
    public Notification create(Notification notification){
        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification update(Notification notification){
        return notificationRepository.save(notification);
    }

    @Transactional
    public void delete(long notificationId){
         notificationRepository.delete(notificationId);
    }

    public List<Notification> getByUserId(long userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getByUserIdAndProcessed(long userId, boolean processed, LocalDateTime localDateTime) {
        return notificationRepository.findByUserIdAndProcessedAndShowTime(userId, processed, localDateTime);
    }

    @Transactional
    public void turnOffNotification(User user, String apiUserRestUrl) {
            Notification notification = notificationRepository.findByUserIdAndNotificationTypeApiUrlAndProcessed(user.getId(), apiUserRestUrl, false);
            notification.setProcessed(true);
            notificationRepository.save(notification);
            LOGGER.debug("Change status of notification {}", notification);
    }

    public Notification getOne(long notificationId) {
        return  notificationRepository.findOne(notificationId);
    }
}
