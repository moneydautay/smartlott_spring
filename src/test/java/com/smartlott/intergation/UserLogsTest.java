package com.smartlott.intergation;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserLogs;
import com.smartlott.backend.persistence.repositories.UserLogsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserLogsTest extends AbstractIntegrationTest {

    @Autowired
    private UserLogsRepository userLogsRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeTest() throws Exception{
        Assert.assertNotNull(userLogsRepository);
    }

    @Test
    public void userLogsTest() throws Exception{

        //create user
        User user = createCustUser(testName);

        Assert.assertNotNull(user);


        LocalDateTime localDateTime =  LocalDateTime.now(Clock.systemUTC());

        UserLogs userLogs = new UserLogs("Add account for user", localDateTime , user);

        userLogsRepository.save(userLogs);

        Assert.assertNotNull(userLogs);

        //find UserLogs by user
        List<UserLogs> userLogss1 = userLogsRepository.findByUser(user);

        Assert.assertEquals("Userlogs is equal 1", 1 , userLogss1.size());

        //find UserLogs by createdDate
        List<UserLogs> userLogss2 = userLogsRepository.findByCreatedDate(localDateTime);

        Assert.assertEquals("Userlogs must be equal 1", 1, userLogss2.size());
    }
}
