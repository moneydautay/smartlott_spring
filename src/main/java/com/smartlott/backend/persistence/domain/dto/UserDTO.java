package com.smartlott.backend.persistence.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smartlott.backend.persistence.domain.backend.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by greenlucky on 4/1/17.
 */
public class UserDTO {

    private long id;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    private String phoneNumber;

    private String profileImage;

    private String documentOne;

    private String documentTwo;

    private int sex;

    private boolean enabled = true;

    private double cash;

    private LocalDateTime createDate;

    private boolean actived = false;

    private User activeBy;


    private Set<Address> addresses = new HashSet<>();

    private User introducedBy;

    private String introducedKey;

    private Set<UserCash> userCashes = new HashSet<>();

    private UserInvestment userInvestment;
}
