package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Password;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.PasswordRepository;
import com.smartlott.backend.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmartlottApplication.class)
public class UserRepositoryIntergationTest extends  AbstractIntegrationTest{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Rule public TestName testName = new TestName();

    @Before
    public void init() throws Exception{
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void testFindUserByEmail() throws Exception{
        String email = testName.getMethodName()+"@gmail.com";
        createCustUser(testName);

        User user = userRepository.findByEmail(email);
        System.out.print(user.toString());

        List<Password> passwords = passwordRepository.findByUserAndEnabled(user,true);

        System.out.println(passwords);

        Assert.assertNotNull(user);
    }
}
