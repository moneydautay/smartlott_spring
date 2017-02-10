package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.persistence.repositories.*;
import com.smartlott.enums.InvestmentPackageEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmartlottApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    private InvestmentPackageRepository packageRepository;

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
        User user = userRepository.findOne(Long.valueOf(1));
        double cash = user.getCash();
        System.out.printf("Cash of user: "+ cash);
        cash -= 0.01;
        System.out.printf("Cash of user after minus 0.01: "+ cash);
        userRepository.updateCash(user.getId(), cash);
        User actualUser = userRepository.findOne((long)1);
        System.out.println(actualUser.toString());
    }

    @Test
    public void getUserByRoleName() throws Exception{
        String ROLE_NAME = "ROLE_CUSTOMER";
        Page<User> users = userRepository.findByUserRolesRoleName(ROLE_NAME, new PageRequest(0,10));
        System.out.println(users.getContent());
    }

    @Test
    public void getUserInvestment() throws Exception{
        User user = userRepository.findOne(Long.valueOf(1));
        System.out.println(user.getUserInvestment());
    }


}
