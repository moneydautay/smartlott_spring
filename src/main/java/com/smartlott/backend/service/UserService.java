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

    /**
     * Findy a user by username
     * @param username given by user
     * @return A user or not if not found
     */
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * Findy a user by email
     * @param email given by user
     * @return A user or not if not found
     */
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    /**
     * Create a user
     * @param user
     */
    @Transactional
    public User createUser(User user){

        String encryptPassword = passwordEncoder.encode(user.getPassword());

        Set<Password> passwords = new HashSet<>();
        passwords.add(new Password(encryptPassword, LocalDateTime.now(Clock.systemUTC()), user));
        user.setPassword(encryptPassword);

        user.setPasswords(passwords);
        user.setCreateDate(LocalDateTime.now(Clock.systemUTC()));

        user = userRepository.save(user);

        return user;
    }


    /**
     * Find user by id
     * @param id given by user
     * @return a user if null if not found
     */
    public User findOne(long id) {
        return userRepository.findOne(id);
    }

    /**
     *Update user
     * @param user
     * @return user after update
     */
    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
