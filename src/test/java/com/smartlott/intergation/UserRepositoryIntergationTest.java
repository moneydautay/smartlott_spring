package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Address;
import com.smartlott.backend.persistence.domain.backend.Password;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserRole;
import com.smartlott.backend.persistence.repositories.AddressRepository;
import com.smartlott.backend.persistence.repositories.PasswordRepository;
import com.smartlott.backend.persistence.repositories.UserRepository;
import com.smartlott.backend.persistence.repositories.UserRoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
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

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AddressRepository addressRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Rule public TestName testName = new TestName();

    @Before
    public void init() throws Exception{
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void testFindUserByEmail() throws Exception{

        Address address = createAddress(testName);

        Assert.assertNotNull(address);


        String email = testName.getMethodName()+"@gmail.com";
        createCustUser(testName);

        User user = userRepository.findByEmail(email);

        List<Password> passwords = passwordRepository.findByUserAndEnabled(user,true);


        List<UserRole> userRoles = userRoleRepository.findAll();

        address.setUser(user);

        addressRepository.save(address);

        System.out.println(address);

        Assert.assertNotNull(user);

        Assert.assertNotNull(passwords);

        Assert.assertNotNull(userRoles);

        //find address by userId
        List<Address> addresses = addressRepository.findByUserId(user.getId());

        System.out.println(addresses);

        System.out.println(user);
        //find user by usename and password
        String encodedPassword = "$2a$12$2amqGuI0WhE4DC3uqvTvV..ezmwpPzAYlR4YvS13EwzwhHN05FHF2";
        User user1 = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        Assert.assertNotNull(user1);

    }

    @Test
    public void updateCashUser(){

    }

}
