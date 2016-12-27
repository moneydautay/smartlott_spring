package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.SecurityToken;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.SecurityTokenRepository;
import com.smartlott.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
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

        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        securityToken = new SecurityToken(token, user, now, tokenExpirationLengthInMinutes);

        securityToken = tokenRepository.save(securityToken);

        LOGGER.debug("Successfully created token {} for user {}", securityToken, user);

        return securityToken;
    }
}
