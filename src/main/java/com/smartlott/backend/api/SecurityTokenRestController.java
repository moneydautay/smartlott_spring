package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.SecurityToken;
import com.smartlott.backend.persistence.repositories.EmailService;
import com.smartlott.backend.service.SecurityTokenSevice;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * Created by Mrs Hoang on 27/12/2016.
 */
@RestController
@RequestMapping(SecurityTokenRestController.SECURITY_TOKEN_URL)
public class SecurityTokenRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityTokenRestController.class);

    public static final String SECURITY_TOKEN_URL = "api/security-token";

    @Autowired
    private SecurityTokenSevice securityTokenSevice;

    @Autowired
    private EmailService emailService;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewToken(@RequestBody String email, Locale locale){
        SecurityToken securityToken = securityTokenSevice.createSecurityTokenForEmail(email.trim());

        String message = messageSource.getMessage("security.token.message.text", new Object[]{securityToken}, locale);
        String subject = messageSource.getMessage("security.token.subject.text", null, locale);

        //sending mail to email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(webmasterEmail);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailService.sendGenericEmailMessage(mailMessage);

        LOGGER.debug("Sent email  with content {} to email {}", message, email);

        return new ResponseEntity<Object>(securityToken, HttpStatus.OK);
    }
}
