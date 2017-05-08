package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.repositories.*;
import com.smartlott.utils.FormatNumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
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

    @Autowired
    private NetworkLevelRepository levelRepository;

    @Value("${default.user.id.get.cash}")
    private long defaultUserIdGetCash=0;

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
    public double saveBonusOfUser(User user, double amount){
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        double valueBonus  = 0;

        //get all network level is enabled
        List<NetworkLevel> networkLevels = levelRepository.findByEnabled(true);

        for (NetworkLevel networkLevel : networkLevels) {

            //get List ancestor of user
            Network network = networkRepository.findByOfUserIdAndNetworkLevelId(user.getId(), networkLevel.getId());

            valueBonus = (amount*networkLevel.getIncomeComponent().getValue())/100;

            User ancestor = null;
            UserCash userCash = null;
            if(network != null
                    && network.getAncestor().getUserInvestment().getInvestmentPackage().getLevelNetwork()
                    >= network.getNetworkLevel().getLevel()) {

                ancestor = network.getAncestor();
                userCash = userCashRepository.findByUserIdAndCash_Received(ancestor.getId(), true);
            }else {
                userCash = userCashRepository.findByUserIdAndCash_Received(defaultUserIdGetCash, true);
                ancestor = userCash.getUser();
            }

            Bonus bonus = new Bonus(valueBonus, ancestor, user, now, networkLevel.getLevel());

            bonus = createNew(bonus);

            LOGGER.info("Created bonus {}", bonus);

            //update cash or ancestor
            double cash = userCash.getValue();
            cash = cash + valueBonus;
            userCash.setValue(FormatNumberUtils.formatNumber(cash));
            LOGGER.info("Updating User cash {} value {}", userCash.getUser().getEmail(), userCash.getValue());
            userCash = userCashRepository.save(userCash);
            LOGGER.info("Updated User cash {} value {}", userCash.getUser().getEmail(), userCash.getValue());


        }
        return valueBonus;
    }

    public double getBonusInDay(long userId) {
        LocalDate now = LocalDate.now(Clock.systemDefaultZone());
        LocalDateTime fromDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        LocalDateTime toDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);
        return bonusRepository.getSumBonusByUserId(userId, fromDate, toDate);
    }

    public double getTotalBonus(long userId) {
        return bonusRepository.getTotalBonusByUserId(userId);
    }
}
