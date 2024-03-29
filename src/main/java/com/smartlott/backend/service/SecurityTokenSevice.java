package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.SecurityToken;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.EmailService;
import com.smartlott.backend.persistence.repositories.SecurityTokenRepository;
import com.smartlott.backend.persistence.repositories.UserRepository;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Mrs Hoang on 27/12/2016.
 */
@Service
public class SecurityTokenSevice {

    @Autowired
    private SecurityTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Value("${webmaster.email}")
    private String webmasterEmail;

    @Autowired
    private MessageSource messageSource;

    @Value("${token.expiration.length.minutes}")
    private int tokenExpirationLengthInMinutes;

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityTokenSevice.class);

    /**
     * find security token by token
     * @param token
     * @return A token or null if not found
     */
    public SecurityToken getSecurityTokenByToken(String token){
        return tokenRepository.findByToken(token);
    }


    public List<SecurityToken> getSecurityTokenByUserId(long userId){
        return tokenRepository.findByUserId(userId);
    }

    /**
     *
     * @param username
     * @return
     */
    public SecurityToken createSecurityTokenForUsername(String username){

        SecurityToken securityToken = null;

        User user = userRepository.findByUsername(username);
        if(user != null)
            securityToken = createNewSecurityTokenForUser(user);
        else
            LOGGER.error("Countn't find a user for the given username {}", username);

        return  securityToken;
    }

    public SecurityToken createSecurityTokenForEmail(String email) {
        SecurityToken securityToken = null;

        User user = userRepository.findByEmail(email);
        if(user != null)
            securityToken = createNewSecurityTokenForUser(user);
        else
            LOGGER.error("Countn't find a user for the given email {}", email);

        return  securityToken;
    }

    public SecurityToken createNewSecurityTokenForUser(User user) {
        SecurityToken securityToken = null;

        String token = UUID.randomUUID().toString().substring(0,8);
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        securityToken = new SecurityToken(token, user, now, tokenExpirationLengthInMinutes);

        securityToken = tokenRepository.save(securityToken);

        LOGGER.debug("Successfully created token {} for user {}", securityToken, user);

        return securityToken;
    }

    /**
     * Delete token by id
     * @param id
     */
    public void remove(long id) {
        tokenRepository.delete(id);
    }

    public void sendSecurityTokenEmail(String email, Locale locale){
        SecurityToken securityToken = createSecurityTokenForEmail(email.trim());

        String message = messageSource.getMessage("security.token.message.text", new Object[]{securityToken}, locale);
        String subject = messageSource.getMessage("security.token.subject.text", null, locale);

        //sending mail to email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(webmasterEmail);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailService.sendGenericEmailMessage(mailMessage);
        System.out.println("Sent email  with content ["+message+"] to email {"+ email+"}");
        LOGGER.debug("Sent email  with content {} to email {}", message, email);
    }

    public SecurityToken checkValidToken(String strSecurityToken, List<MessageDTO> messageDTOS, Locale locale) {
        //check security token
        SecurityToken securityToken = getSecurityTokenByToken(strSecurityToken);
        if(securityToken == null || LocalDateTime.now(Clock.systemDefaultZone()).isAfter(securityToken.getExpiryDate())){
            LOGGER.error("Token {} is not valid", securityToken);
            String message = messageSource.getMessage("security.token.invalid", new Object[]{securityToken} , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
           return null;
        }
        return securityToken;
    }
}
