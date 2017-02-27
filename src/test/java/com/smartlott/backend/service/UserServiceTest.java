package com.smartlott.backend.service;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
public class UserServiceTest {

    private UserService userService;


    @Before
    public void before() throws Exception{
        userService = new UserService();
    }

    @Test
    public void test() throws Exception{
        String introducedKey = userService.createIntroducedKey("admin");
        System.out.println(introducedKey);
        introducedKey = userService.createIntroducedKey("greenlucky");
        System.out.println(introducedKey);
    }

}