package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.NotificationType;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Mrs Hoang on 29/12/2016.
 */
@Service
public class NotificationTypeService {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;


    public List<NotificationType> getAll(){
        return notificationTypeRepository.findAll();
    }

    public void creates(List<NotificationType> notificationTypes) {
        notificationTypeRepository.save(notificationTypes);
    }
}
