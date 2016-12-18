package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Password;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    /**
     * Create a user
     * @param user
     */
    public User createUser(User user){

        User localUser = userRepository.findByEmail(user.getEmail());

        if(localUser != null){
            LOGGER.error("User with username {} and email {} already exist. Nothing will be done.", localUser.getUsername(), localUser.getEmail());
        }else{
            String encryptPassword = passwordEncoder.encode(user.getPassword());

            Set<Password> passwords = new HashSet<>();
            passwords.add(new Password(encryptPassword, LocalDateTime.now(Clock.systemUTC()), localUser));
            user.setPassword(encryptPassword);

            user.setPasswords(passwords);

            localUser = userRepository.save(user);
        }

        return localUser;
    }


}
