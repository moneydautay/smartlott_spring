package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.UserCash;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by Mrs Hoang on 11/02/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserCashRepositoryTest {

    @Autowired
    private UserCashRepository userCashRepository;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(userCashRepository);
    }

    @Test
    public void test() throws Exception{

        UserCash userCash = userCashRepository.findByUserIdAndCash_Received(1, true);
        System.out.println(userCash);
    }

}