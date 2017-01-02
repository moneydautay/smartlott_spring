package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Bonus;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.BonusRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class BonousTest extends AbstractIntegrationTest{

    @Autowired
    private BonusRepository bonusRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeTest() throws  Exception{
        Assert.assertNotNull(bonusRepository);
    }

    @Test
    public void bonousTest() throws Exception{
        User user1 = createCustUser(testName);

        Bonus bonous = new Bonus(0.1 ,user1, user1, LocalDateTime.now(Clock.systemUTC()),1);

        bonusRepository.save(bonous);

        List<Bonus> bonouss = bonusRepository.findByOfUser(user1);

        Assert.assertEquals("List Bonus mus be equal 1", 1, bonouss.size());

    }
}
