package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Cash;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserCash;
import com.smartlott.backend.persistence.repositories.CashRepository;
import com.smartlott.backend.persistence.repositories.UserCashRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greenlucky on 2/4/17.
 */
@Service
@Transactional(readOnly = true)
public class UserCashService {
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCashService.class);


    @Autowired
    private UserCashRepository userCashRepository;

    @Autowired
    private CashRepository cashRepository;

    /**
     * Creates new User cash given by user id, cash id and value
     * @param userId
     * @param cashId
     * @param value
     * @return new UserCash after create
     */
    @Transactional
    public UserCash create(long userId, int cashId, double value){
        UserCash userCash = createUtils(userId, cashId, value);
        return userCashRepository.save(userCash);
    }

    @Transactional
    public List<UserCash> createAllCashForUser(long userId){
        User user = new User();
        user.setId(userId);

        List<Cash> cashs = cashRepository.findAll();
        List<UserCash> userCashes = new ArrayList<>();
        for (Cash cash: cashs) {
            userCashes.add(createUtils(userId, cash.getId(), 0.0));
        }

        return userCashRepository.save(userCashes);
    }

    @Transactional
    public UserCash update(UserCash userCash){
        return  userCashRepository.save(userCash);
    }


    /**
     * Updates cash of user given by user id, cash id and value
     * @param userId
     * @param cashId
     * @param value
     * @return User cash after update
     */
    @Transactional
    public UserCash update(long userId, int cashId, double value){
        UserCash userCash = userCashRepository.findByUserIdAndCashIdAndEnabled(userId, cashId,true);
        double currentCash = userCash.getValue();

        LOGGER.info("User {} cash {} before update cash: {} ",userCash.getUser().getId(), userCash.getCash().getName(), userCash.getValue());

        currentCash += value;
        userCash.setValue(currentCash);
        userCash = userCashRepository.save(userCash);

        LOGGER.info("User {} cash {} after update cash: {} ",userCash.getUser().getId(), userCash.getCash().getName(), userCash.getValue());
        return userCash;
    }



    public UserCash createUtils(long userId, int cashId, double value) {
        User user = new User();
        user.setId(userId);
        Cash cash = new Cash();
        cash.setId(cashId);
        UserCash userCash = new UserCash();
        userCash.setUser(user);
        userCash.setCash(cash);
        userCash.setValue(value);
        return userCash;
    }

    public List<UserCash> getAll() {

        return userCashRepository.findAll();
    }


    public List<UserCash> getUserCashByUserId(long userId) {
        return userCashRepository.findByUserIdAndEnabled(userId, true);
    }

    public UserCash getUserCashByUserCashId(long userCashId) {
        return userCashRepository.findOne(userCashId);
    }

    public UserCash getByUserIdAndCash_Received(long userCashId, boolean received) {
        return userCashRepository.findByUserIdAndCash_Received(userCashId, received);
    }

    @Transactional
    public UserCash update(long userCashId, double v) {

        UserCash userCash = userCashRepository.findOne(userCashId);

        LOGGER.info("User {} cash {} before update cash: {} ",userCash.getUser().getId(), userCash.getCash().getName(), userCash.getValue());
        
        double currentCash = userCash.getValue();
        currentCash += v;
        userCash.setValue(currentCash);
        userCash = userCashRepository.save(userCash);

        LOGGER.info("User {} cash {} after update cash: {} ",userCash.getUser().getId(), userCash.getCash().getName(), userCash.getValue());

        return userCash;
    }

    public UserCash getByUserIdAndCash_Id(long id, int cashId, boolean enabled) {
        return userCashRepository.findByUserIdAndCashIdAndEnabled(id, cashId, enabled);
    }
}
