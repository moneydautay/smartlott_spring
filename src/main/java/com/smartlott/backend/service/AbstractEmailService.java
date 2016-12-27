package com.smartlott.backend.service;

import com.smartlott.backend.persistence.repositories.EmailService;
import com.smartlott.web.domain.frontend.FeedbackPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by Mrs Hoang on 27/12/2016.
 */
@Service
public abstract class AbstractEmailService implements EmailService{

    @Value("${default.to.address}")
    private String defaultToAddress;

    protected SimpleMailMessage prepareSimpleMailMessageFromFeedbackPojo(FeedbackPojo feedback){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(defaultToAddress);
        message.setFrom(feedback.getEmail());
        message.setSubject("[Devops Greenlucky]: Feedback receive from "+ feedback.getFirstName() + " "+ feedback.getLastName()+"!");
        message.setText(feedback.getFeedback());
        return message;
    }

    @Override
    public void sendFeedbackEmail(FeedbackPojo feedback){
        sendGenericEmailMessage(prepareSimpleMailMessageFromFeedbackPojo(feedback));
    }

}
