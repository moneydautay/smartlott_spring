package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Bonus;
import com.smartlott.backend.persistence.domain.backend.Network;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserCash;
import com.smartlott.backend.persistence.repositories.BonusRepository;
import com.smartlott.backend.persistence.repositories.NetworkRepository;
import com.smartlott.backend.persistence.repositories.UserCashRepository;
import com.smartlott.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by greenlucky on 1/2/17.
 */
@Service
@Transactional(readOnly = true)
public class BonusService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(BonusService.class);

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private NetworkRepository networkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCashRepository userCashRepository;

    /**
     * Create new bonus
     * @param bonus
     * @return a bonus or null if occurs error
     */
    @Transactional
    public Bonus createNew(Bonus bonus){
        bonus = bonusRepository.save(bonus);
        return bonus;
    }

    /**
     * Retrieve all bonus
     * @return A list of bonus or null if empty
     */
    public Page<Bonus> getAll(Pageable pageable){
        return bonusRepository.findAll(pageable);
    }

    public List<Bonus> getAllByUserId(long userId) {
        return bonusRepository.findByOfUserId(userId);
    }

    public Page<Bonus> getAllByUserId(long userId, Pageable pageable) {
        return bonusRepository.findByOfUserId(userId, pageable);
    }

    @Transactional
    public double saveBonousOfUser(User user, double amount){
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        double valueBonus  = 0;
        //get List ancestor of user
        List<Network> networks = networkRepository.findByOfUserId(user.getId());
        for (Network network : networks){
            valueBonus = (amount*network.getNetworkLevel().getIncomeComponent().getValue())/100;
            User ancestor = network.getAncestor();
            Bonus bonus = new Bonus(valueBonus, ancestor , user, now ,network.getNetworkLevel().getId());
            bonus = createNew(bonus);

            UserCash userCash = userCashRepository.findByUserIdAndCash_Received(ancestor.getId(), true);

            //update cash or ancestor
            double cash = userCash.getValue();
            cash += valueBonus;
            userCash.setValue(cash);
            LOGGER.info("Updating cash of user {}", ancestor);
            userCash = userCashRepository.save(userCash);
            LOGGER.info("User cash {}", userCash);
            LOGGER.info("Updated cash of user {}", ancestor);

            LOGGER.info("Created bonus {}", bonus);

        }
        return valueBonus;
    }
}
