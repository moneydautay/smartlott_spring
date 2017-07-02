package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.repositories.BonusRepository;
import com.smartlott.backend.persistence.repositories.NetworkLevelRepository;
import com.smartlott.backend.persistence.repositories.NetworkRepository;
import com.smartlott.backend.persistence.repositories.UserCashRepository;
import com.smartlott.enums.BonusType;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by greenlucky on 1/2/17.
 */
@Service
@Transactional(readOnly = true)
public class BonusService {

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BonusService.class);

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private NetworkRepository networkRepository;


    @Autowired
    private UserCashRepository userCashRepository;

    @Autowired
    private NetworkLevelRepository levelRepository;

    @Autowired
    private LotteryDialingService dialingService;

    @Value("${default.user.id.get.cash}")
    private long defaultUserIdGetCash = 0;

    @Value("${divide.network.level.award}")
    private int divideNetworkLevelAward;

    /**
     * Create new bonus
     *
     * @param bonus
     * @return a bonus or null if occurs error
     */
    @Transactional
    public Bonus createNew(Bonus bonus) {
        bonus = bonusRepository.save(bonus);
        return bonus;
    }

    /**
     * Retrieve all bonus
     *
     * @return A list of bonus or null if empty
     */
    public Page<Bonus> getAll(Pageable pageable) {
        return bonusRepository.findAll(pageable);
    }

    public List<Bonus> getAllByUserId(long userId) {
        return bonusRepository.findByOfUserId(userId);
    }

    public Page<Bonus> getAllByUserId(long userId, Pageable pageable) {
        return bonusRepository.findByOfUserId(userId, pageable);
    }

    @Transactional
    public double saveBonusOfUser(User user, double amount, BonusType bonusType) {
        double valueBonus = 0;
        try {
            //get all network level is enabled
            List<NetworkLevel> networkLevels = levelRepository.findByEnabledAndBonusType(true, bonusType);

            for (NetworkLevel networkLevel : networkLevels) {

                //get List ancestor of user
                Network network = networkRepository.findByOfUserIdAndNetworkLevelId(user.getId(), networkLevel.getLevel());

                valueBonus = (amount * networkLevel.getIncomeComponent().getValue()) / 100;

                doBonusForUserAncestor(network, user, valueBonus, networkLevel.getLevel(), bonusType);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return valueBonus;
    }

    public void doBonusForUserAncestor(Network network, User fromUser, double valueBonus, int level, BonusType bonusType) {
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        UserCash userCash = null;
        if (network != null
                && network.getAncestor().getUserInvestment().getInvestmentPackage().getLevelNetwork()
                >= network.getNetworkLevel().getLevel()) {
            userCash = userCashRepository.findByUserIdAndCash_Received(network.getAncestor().getId(), true);
        } else {
            userCash = userCashRepository.findByUserIdAndCash_Received(defaultUserIdGetCash, true);
        }

        Bonus bonus = new Bonus(valueBonus, userCash.getUser(), fromUser, now, level, bonusType);

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

    @Transactional
    public void saveBonusAwardOfUser(User user, double amount, BonusType bonusType) {
        try {
            //get List ancestor of user
            Network network = networkRepository.findByOfUserIdAndNetworkLevelId(user.getId(), divideNetworkLevelAward);
            doBonusForUserAncestor(network, user, amount, divideNetworkLevelAward, bonusType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public double getBonusInTerm(long userId) {

        //get current dialing lottery
        LotteryDialing lotteryDialing = dialingService.getOpenedLotteryDialing(true);

        if (null != lotteryDialing) {
            LocalDateTime fromDate = lotteryDialing.getFromDate();
            LocalDateTime toDate = lotteryDialing.getToDate();
            return bonusRepository.getSumBonusByUserId(userId, fromDate, toDate);
        }
        return 0.0;
    }

    public double getTotalBonus(long userId) {
        return bonusRepository.getTotalBonusByUserId(userId);
    }
}
