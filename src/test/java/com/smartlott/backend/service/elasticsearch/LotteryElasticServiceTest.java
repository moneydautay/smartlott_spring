package com.smartlott.backend.service.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.LotteryElastic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by greenlucky on 4/19/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LotteryElasticServiceTest {

    @Autowired
    private LotteryElasticService lotteryElasticService;

    @Test
    public void getAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<LotteryElastic> lotteryElastics = lotteryElasticService.getAll(pageRequest);
        System.out.println(lotteryElastics.getContent());
    }

    @Test
    public void search() throws Exception {
        String query = "23-23-23-23-23-23";
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<LotteryElastic> lotteryElastics = lotteryElasticService.search(query, pageRequest);
        System.out.println(lotteryElastics.getContent());
    }

}