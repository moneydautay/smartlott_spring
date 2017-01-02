package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Bonus;
import com.smartlott.backend.persistence.repositories.BonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by greenlucky on 1/2/17.
 */
@Service
@Transactional(readOnly = true)
public class BonusService {

    @Autowired
    private BonusRepository bonusRepository;

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

    public Page<Bonus> getAllByUserId(long userId, Pageable pageable) {
        return bonusRepository.findByOfUserId(userId, pageable);
    }
}
