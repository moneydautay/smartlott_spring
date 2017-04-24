package com.smartlott.backend.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by greenlucky on 4/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleHandlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllRole() throws Exception {

        Object result = restTemplate.getForEntity("http://localhost:8080/api/role/all", Object.class);

        System.out.println(result);
    }

}