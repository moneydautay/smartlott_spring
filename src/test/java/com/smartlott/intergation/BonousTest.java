package com.smartlott.intergation;

import com.smartlott.backend.persistence.domain.backend.Bonus;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.BonusService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonousTest extends AbstractIntegrationTest{

    @Autowired
    private BonusService bonusService;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeTest() throws  Exception{
        Assert.assertNotNull(bonusService);
    }

    @Test
    public void bonousTest() throws Exception{

        User admin = userRepository.findOne(Long.valueOf(1));
        System.out.println("Before upate cash: "+admin.getCash());
        User user1 = userRepository.findOne(Long.valueOf(2));
        Assert.assertNotNull(user1);
        bonusService.saveBonusOfUser(user1, 1);

        List<Bonus> bonouss = bonusService.getAllByUserId(Long.valueOf(1));

        admin = userRepository.findOne(Long.valueOf(1));
        System.out.println("After upate cash: "+admin.getCash());
        Assert.assertEquals("List Bonus mus be equal 1", 1, bonouss.size());

    }

}
