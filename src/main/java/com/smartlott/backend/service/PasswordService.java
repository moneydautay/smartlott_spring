package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Password;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.PasswordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Created by greenlucky on 12/23/16.
 */
@Service
@Transactional(readOnly = true)
public class PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Checking exist password
     * @param user
     * @param password
     * @return true if password exist or null if not
     */
    public boolean existedPassword(User user, String password){
        for (Password pwd : passwordRepository.findByUser(user)) {
            if(passwordEncoder.matches(password, pwd.getPassword()))
                return  true;
        }
        return false;
    }

    /**
     * Change status old password
     * @param localUser user
     * @param enabled: true or false
     */
    public void changeStatusOldPassword(User localUser, boolean enabled) {
        passwordRepository.changePasswordByUserIdAndEnabled(localUser.getId(), enabled);
    }


    /**
     * Create new password by user and new password
     * @param user
     * @param newPassword
     * @return a password after created
     */
    public Password createNewPassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        Password password = new Password(encodedPassword, LocalDateTime.now(Clock.systemUTC()), user);

        return passwordRepository.save(password);
    }
}
