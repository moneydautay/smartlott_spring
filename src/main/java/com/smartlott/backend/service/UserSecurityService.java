package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Service
public class UserSecurityService implements UserDetailsService{

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            LOGGER.debug("Username {} not found.", username);
            throw new UsernameNotFoundException("Username "+ username + " not found.");
        }
        return user;
    }
}
