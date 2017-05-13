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
public class ITUserSearchRestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void searchMember() throws Exception {
        String query = "customer";
        Object result = restTemplate.getForEntity(
                "http://localhost:8080/api/user/search/member?q={query}",
                Object.class, query);
        System.out.println(result);
    }

    @Test
    public void searchUser() throws Exception {
        String query = "customer";
        Object result = restTemplate.getForEntity(
                "http://localhost:8080/api/user/search/user?q={query}",
                Object.class, query);
        System.out.println(result);
    }

}