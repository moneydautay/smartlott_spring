package com.smartlott.backend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by greenlucky on 5/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonusServiceTest {

    @Autowired
    private BonusService bonusService;

    @Test
    public void getBonusInDay() throws Exception {
        double bonusInDay = bonusService.getBonusInDay(1);
        System.out.println(bonusInDay);
    }

    @Test
    public void getTotalBonus() throws Exception {
        double totalBonus = bonusService.getTotalBonus(1);
        System.out.println(totalBonus);
    }

}