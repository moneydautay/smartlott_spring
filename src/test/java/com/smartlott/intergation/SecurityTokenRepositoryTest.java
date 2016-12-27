package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.SecurityToken;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.SecurityTokenRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Mrs Hoang on 27/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class SecurityTokenRepositoryTest extends AbstractIntegrationTest{

    @Autowired
    private SecurityTokenRepository securityTokenRepository;

    @Value("${token.expiration.length.minutes}")
    private int expirationTimeMinutes;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(securityTokenRepository);
    }

    @Test
    public void testSecurityTokenExpirationLength() throws Exception{
        User user = createCustUser(testName);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        String token = UUID.randomUUID().toString();

        LocalDateTime expectTime = now.plusMinutes(expirationTimeMinutes);

        SecurityToken securityToken = createSecurityToken(token, user, now);

        LocalDateTime actualTime = securityToken.getExpiryDate();
        Assert.assertNotNull(actualTime);
        Assert.assertEquals(expectTime, actualTime);
    }

    @Test
    public void testFindTokenByValue() throws Exception{
        User user = createCustUser(testName);
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        createSecurityToken(token, user, now);
        SecurityToken securityToken = securityTokenRepository.findByToken(token);
        Assert.assertNotNull(securityToken);
        Assert.assertNotNull(securityToken.getId());
        Assert.assertNotNull(securityToken.getUser());
    }

    @Test
    public void testDeleteToken() throws Exception{

    }

    private SecurityToken createSecurityToken(String token, User user, LocalDateTime now) {
        SecurityToken securityToken = new SecurityToken(token, user, now, expirationTimeMinutes);
        securityToken = securityTokenRepository.save(securityToken);
        Assert.assertNotNull(securityToken);
        return  securityToken;
    }

}
