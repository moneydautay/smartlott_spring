package com.smartlott.backend.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by greenlucky on 3/31/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTokenRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void sendEmailResetPassword() throws Exception {

        restTemplate.getForEntity(
                SecurityTokenRestController.SECURITY_TOKEN_URL + "/reset-password/{userId}?urlrsp={urlrsp}",
                Object.class ,  1, "/user/reset-password");
    }

}