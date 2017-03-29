package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.domain.elastic.UserElastic;
import com.smartlott.backend.persistence.repositories.*;
import com.smartlott.backend.persistence.repositories.elasticsearch.UserElasticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${default.user.cash}")
    private double defaultUserCash;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInvestmentRepository investmentRepository;

    @Autowired
    private CashRepository cashRepository;

    @Autowired
    private UserCashRepository userCashRepository;

    @Autowired
    private InvestmentPackageRepository investmentPackageRepository;

    @Value("${default.investment.package}")
    private int defaultInvestmentPackage;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserElasticRepository userElasticRepository;

    /**
     * Findy a user by username
     *
     * @param username given by user
     * @return A user or not if not found
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Findy a user by email
     *
     * @param email given by user
     * @return A user or not if not found
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    /**
     * Create a user
     *
     * @param user
     */
    @Transactional
    public User createUser(User user) {

        String encryptPassword = passwordEncoder.encode(user.getPassword());
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        Set<Password> passwords = new HashSet<>();
        passwords.add(new Password(encryptPassword, LocalDateTime.now(Clock.systemDefaultZone()), user));
        user.setPassword(encryptPassword);

        user.setPasswords(passwords);
        user.setCreateDate(now);

        //reset introducedKey to null
        user.setIntroducedKey(null);
        //save user
        user = userRepository.save(user);

        //add cash to user
        addCashToUser(user);

        //add basic user investment package is CUSTOMER
        UserInvestment userInvestment = addInvestmentPackage(user, defaultInvestmentPackage, now);

        user.addUserInvestment(userInvestment);

        UserElastic userElastic = new UserElastic(user);
        LOGGER.info("Creating User elastic {} ", userElastic.toString());
        userElasticRepository.save(userElastic);

        return user;
    }

    /**
     * Find user by id
     *
     * @param id given by user
     * @return a user if null if not found
     */
    public User findOne(long id) {
        return userRepository.findOne(id);
    }

    /**
     * Update user
     *
     * @param user
     * @return user after update
     */
    @Transactional
    public User updateUser(User user) {
        user = userRepository.save(user);

        //update user elastic searchAll
        UserElastic userElastic = new UserElastic(user);
        userElasticRepository.save(userElastic);

        return user;
    }

    public User checkingPassword(String username, String currentPassword) {
        User localUser = userRepository.findByUsername(username);
        if (passwordEncoder.matches(currentPassword, localUser.getPassword()))
            return localUser;
        return null;
    }

    @Transactional
    public void changePassword(String username, String newPassword) {
        userRepository.changePassword(username, newPassword);
    }

    /**
     * Get user by introduced key
     *
     * @param introducedKey
     * @return A user or null if not found
     */
    public User getUserByIntroducedKey(String introducedKey) {
        return userRepository.findByIntroducedKey(introducedKey);
    }

    /**
     * Dedicates to update cash of user
     *
     * @param userId
     * @param cash
     */
    @Transactional
    public void updateCash(Long userId, double cash) {
        userRepository.updateCash(userId, cash);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getByRole(String roleName, Pageable pageable) {
        return userRepository.findByUserRolesRoleName(roleName, pageable);
    }

    @Transactional
    public UserInvestment addInvestmentPackage(User user, int investmentPackageId, LocalDateTime from) {

        InvestmentPackage investmentPackage = investmentPackageRepository.findOne(investmentPackageId);

        UserInvestment userInvestment = new UserInvestment();
        userInvestment.setUser(user);
        userInvestment.setInvestmentPackage(investmentPackage);
        userInvestment.setFromDate(from);
        if (investmentPackage.isLimitTime())
            userInvestment.setToDate(from.plusDays(investmentPackage.getDurationTime()));

        userInvestment = investmentRepository.save(userInvestment);

        //add user introduced key if not exist
        if (investmentPackage.getLevelNetwork() > 0 && user.getIntroducedKey() == null) {
            userRepository.updateIntroducedKey(user.getId(), createIntroducedKey(user.getUsername()));
        }

        return userInvestment;
    }

    public String createIntroducedKey(String username) {
        //set introduced key
        byte[] charKey = username.getBytes();
        String introducedKey = UUID.nameUUIDFromBytes(charKey).toString().replace("-", "").substring(0, 8);
        return introducedKey;
    }

    /**
     * Add cash to user
     *
     * @param user
     */
    private void addCashToUser(User user) {
        //get all cash
        List<Cash> cashes = cashRepository.findByEnabled(true);
        cashes.forEach(ru -> userCashRepository.save(new UserCash(user, ru, defaultUserCash)));

    }
}
