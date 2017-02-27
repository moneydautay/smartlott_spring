package com.smartlott.backend.persistence.repositories;

import com.smartlott.web.domain.frontend.FeedbackPojo;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Mrs Hoang on 27/12/2016.
 */
public interface EmailService {

    /**
     * Sends email with content feedbackPojo
     * @param feedbackPojo
     */
    void sendFeedbackEmail(FeedbackPojo feedbackPojo);

    /**
     * Sends email with content simple mail message objects
     * @param simpleMailMessage
     */
    void sendGenericEmailMessage(SimpleMailMessage simpleMailMessage);
}
