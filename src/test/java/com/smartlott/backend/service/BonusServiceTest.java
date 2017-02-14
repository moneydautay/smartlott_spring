package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserCash;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Mrs Hoang on 11/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonusServiceTest {

    @Autowired
    private BonusService bonusService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCashService userCashService;

    @Before
    public void init() throws Exception{
        Assert.assertNotNull(bonusService);
    }

    @Test
    public void testCalculateNetworkBonus() throws Exception{
        User user = userService.findOne(9);

        bonusService.saveBonusOfUser(user, 1);

        List<UserCash> userCashes = userCashService.getAll();
        for (UserCash uc: userCashes) {
            if(uc.getCash().isReceived()) {
                System.out.println(uc.getUser().getEmail());
                System.out.println(uc.getValue() + "["+uc.getCash());
            }
        }

    }

}