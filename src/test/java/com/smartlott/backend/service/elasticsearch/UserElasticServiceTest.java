package com.smartlott.backend.service.elasticsearch;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.elastic.UserElastic;
import com.smartlott.backend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by greenlucky on 4/24/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserElasticServiceTest {

    @Autowired
    private UserElasticService userElasticService;

    @Autowired
    private UserService userService;

    @Test
    public void searchAll() throws Exception {
        String query = "admin";

        PageRequest pageRequest = new PageRequest(0, 10);
        Page<UserElastic> userElastics = userElasticService.searchAll(query, true, pageRequest);
        System.out.println(userElastics.getContent());
    }

    @Test
    public void getAllUser() throws  Exception{
        List<User> user = userService.getUserByIds(Arrays.asList(Long.valueOf(2)));
        System.out.println(user.toString());
    }

}