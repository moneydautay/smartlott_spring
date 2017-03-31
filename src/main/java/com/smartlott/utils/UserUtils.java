package com.smartlott.utils;

import com.smartlott.backend.persistence.domain.backend.User;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
public class UserUtils {

    /**
     * Non instantiable
     */
    private UserUtils(){
        throw new AssertionError("Non instantiable");
    }

    public static User createCustUser(String username, String email){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName("First Name");
        user.setLastName("Last Name");
        user.setBirthday(LocalDate.of(1986,05,20));
        user.setPhoneNumber("0000000000000");
        user.setPassword("123456");
        user.setEnabled(true);

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        user.setCreateDate(now);

        return user;
    }

}
