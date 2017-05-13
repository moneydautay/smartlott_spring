package com.smartlott.backend.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by greenlucky on 4/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ITUserRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void activeMember() throws Exception {

        Object object = restTemplate.getForEntity("/api/user/active/{userId}/{byUserId}", Object.class, 1, 1);
        System.out.println(object);

    }

    @Test
    public void changeStatus() throws Exception {

        Object object = restTemplate.getForEntity("/api/user/change-status/{status}/{userId}/{byUserId}",
                Object.class, false, 1, 1);
        System.out.println(object);

    }

}