package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Bonus;
import com.smartlott.backend.service.BonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by greenlucky on 1/2/17.
 */
@RestController
@RequestMapping(BonusHandler.API_BONUS_REST_URL)
public class BonusHandler {

    public static final String API_BONUS_REST_URL = "/api/bonus";

    @Autowired
    private BonusService bonusService;

    /**
     * Get all bonus of user
     * @param userId given by userid
     * @param locale
     * @return A list of bonus or null if not exist
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(@PathVariable long userId, Pageable pageable, Locale locale){
        PageRequest request = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
        Page<Bonus> bonuses = bonusService.getAllByUserId(userId, request);
        return new ResponseEntity<Object>(bonuses, HttpStatus.OK);
    }

    /**
     * Create new bonus for user
     * @param userId
     * @param bonus
     * @param locale
     * @return A bonus after created and exception if occurs error
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@PathVariable long userId, @RequestBody Bonus bonus, Locale locale){
        bonus.setReceivedDate(LocalDateTime.now(Clock.systemDefaultZone()));
        bonus = bonusService.createNew(bonus);
        return new ResponseEntity<Object>(bonus, HttpStatus.OK);
    }

    @GetMapping("/statistic/{userId}")
    public ResponseEntity<Object> getStatistic(@PathVariable long userId, Locale locale) {
        Map<String, Object> response = new HashMap<>();

        double bonusInDay = bonusService.getBonusInDay(userId);
        double totalBonus = bonusService.getTotalBonus(userId);

        response.put("bonusInDay", bonusInDay);
        response.put("totalBonus", totalBonus);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
