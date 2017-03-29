package com.smartlott.backend.persistence.repositories.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.UserElastic;
import com.smartlott.backend.service.elasticsearch.UserElasticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by greenlucky on 3/29/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserElasticRepositoryTest {

    @Autowired
    private UserElasticRepository userElasticRepository;

    @Autowired
    private UserElasticService userElasticService;

    @Test
    public void findAll() throws Exception {

        List<UserElastic> userElastics = userElasticRepository.findAll();
        System.out.println(userElastics.toString());
    }

    @Test
    public void findByUsername() throws Exception{
        String search = "admin";
        PageRequest pageRequest = new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "id"));
        Page<UserElastic> userElastics = userElasticRepository.findByUsername(search, pageRequest);
        System.out.println(userElastics.getContent());
    }

    @Test
    public void searchByString() throws Exception {
        String search = "CUSTOMER";
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<UserElastic> userElastics = userElasticService.searchAll(search, pageRequest);
        System.out.println(userElastics.getContent());
    }

}