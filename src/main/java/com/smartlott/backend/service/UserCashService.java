package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Cash;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserCash;
import com.smartlott.backend.persistence.repositories.CashRepository;
import com.smartlott.backend.persistence.repositories.UserCashRepository;
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

    /**
     * Updates cash of user given by user id, cash id and value
     * @param userId
     * @param cashId
     * @param value
     * @return User cash after update
     */
    @Transactional
    public UserCash update(long userId, int cashId, double value){
        UserCash userCash = userCashRepository.findByUserIdAndCashId(userId, cashId);
        userCash.setValue(value);
        return userCashRepository.save(userCash);
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
}
