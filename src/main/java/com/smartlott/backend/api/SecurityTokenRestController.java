package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.SecurityToken;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.EmailService;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.SecurityTokenSevice;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mrs Hoang on 27/12/2016.
 */
@RestController
@RequestMapping(SecurityTokenRestController.SECURITY_TOKEN_URL)
public class SecurityTokenRestController {

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityTokenRestController.class);

    public static final String SECURITY_TOKEN_URL = "/api/security-token";

    @Autowired
    private UserService userService;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private SecurityTokenSevice securityTokenSevice;

    @Autowired
    private EmailService emailService;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    private List<MessageDTO> messages;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> createNewToken(@PathVariable @RequestBody long userId, Locale locale) {
        messages = new ArrayList<>();

        User localUser = getUser(userId, locale);

        SecurityToken securityToken = securityTokenSevice.createSecurityTokenForEmail(localUser.getEmail());

        String message = i18NService.getMessage("security.token.message.text", new Object[]{securityToken}, locale);
        String subject = i18NService.getMessage("security.token.subject.text", locale);

        sendEmail(localUser.getEmail(), subject, message);

        System.out.println("Sent token " + securityToken + "to email " + localUser.getEmail());
        LOGGER.debug("Sent email  with content {} to email {}", message, localUser.getEmail());

        messages.add(new MessageDTO(MessageType.SUCCESS, i18NService.getMessage("order.transaction.resend.security.token.success.text", localUser.getEmail(), locale)));

        return new ResponseEntity<Object>(messages, HttpStatus.OK);
    }

    @GetMapping("/reset-password/{userId}")
    public ResponseEntity<Object> sendEmailResetPassword(@PathVariable long userId,
                                                         @RequestParam("urlrsp") String urlResetPassword,
                                                         Locale locale) {

        messages = new ArrayList<>();

        User localUser = getUser(userId, locale);

        SecurityToken securityToken = securityTokenSevice.createSecurityTokenForEmail(localUser.getEmail());
        urlResetPassword += "?tk=" + securityToken.getToken();
        LocalDateTime expiryDate = securityToken.getExpiryDate();

        String message = i18NService.getMessage(
                "security.message.reset.password.text", new Object[]{localUser.getFirstName(), urlResetPassword, expiryDate}, locale);
        String subject = i18NService.getMessage(
                "security.message.reset.password.subject.text", locale);

        sendEmail(localUser.getEmail(), subject, message);

        LOGGER.debug("Sent email  with content [{}] to email {}", message, localUser.getEmail());

        messages.add(new MessageDTO(MessageType.SUCCESS, i18NService.getMessage(
                        "user.sent.link.reset.password.to.email.text", localUser.getEmail(), locale)));

        return new ResponseEntity<Object>(messages, HttpStatus.OK);
    }

    private void sendEmail(String email, String subject, String message) {

        //sending mail to email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(webmasterEmail);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailService.sendGenericEmailMessage(mailMessage);
    }

    private User getUser(long userId, Locale locale) {
        User localUser = userService.findOne(userId);
        //Checking valid username and password
        if (localUser == null) {
            LOGGER.error("user Id {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("Id.user.not.found", locale));
            throw new NotFoundException(messageDTO);
        }
        return localUser;
    }
}
