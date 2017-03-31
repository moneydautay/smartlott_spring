package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by greenlucky on 3/31/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserByIds() throws Exception {

        List<Long> ids = Arrays.asList(Long.valueOf(1));

        List<User> userList = userService.getUserByIds(ids);

        Assert.assertEquals(1, userList.size());

    }

}