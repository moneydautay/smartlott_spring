package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Bonous;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.BonousRepository;
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
    private BonousRepository bonousRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeTest() throws  Exception{
        Assert.assertNotNull(bonousRepository);
    }

    @Test
    public void bonousTest() throws Exception{
        User user1 = createCustUser(testName);

        Bonous bonous = new Bonous(user1, user1, LocalDateTime.now(Clock.systemUTC()));

        bonousRepository.save(bonous);

        List<Bonous> bonouss = bonousRepository.findByOfUser(user1);

        Assert.assertEquals("List Bonous mus be equal 1", 1, bonouss.size());

    }
}
