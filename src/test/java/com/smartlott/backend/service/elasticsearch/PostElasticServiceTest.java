package com.smartlott.backend.service.elasticsearch;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Category;
import com.smartlott.backend.persistence.domain.backend.Post;
import com.smartlott.backend.persistence.domain.elastic.PostElastic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by greenlucky on 2/3/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class PostElasticServiceTest {

    @Autowired
    private PostElasticService postElasticService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(postElasticService);

        elasticsearchTemplate.deleteIndex(PostElastic.class);

        Category cat1 = new Category();
        cat1.setId(1);
        cat1.setTitle("Tin tuc trung thuong");

        Category cat2 = new Category();
        cat2.setId(2);
        cat2.setTitle("Tin tuc moi");

        //insert new data to elastic search
        Post postElastice1 = new Post();
        postElastice1.setId(1);
        postElastice1.setTitle("This is elastic search post in spring boot");
        postElastice1.setCategories(Arrays.asList(cat1, cat2));
        postElasticService.create(postElastice1);

        Post postElastice2 = new Post();
        postElastice2.setId(2);
        postElastice2.setTitle("The man got the jackpot of smartlott");
        postElastice2.setCategories(Arrays.asList(cat2));
        postElasticService.create(postElastice2);
    }

    @Test
    public void test() throws Exception{
        Page<PostElastic> postElastices = postElasticService.searchByTitle("jackpot", new PageRequest(0,10));
        System.out.println(postElastices.getContent());
    }

    @Test
    public void update() throws Exception{
        Category cat1 = new Category();
        cat1.setId(1);
        cat1.setTitle("Tin tuc trung thuong");
        Category cat2 = new Category();
        cat2.setId(2);
        cat2.setTitle("Tin tuc moi");
        Post postElastice2 = new Post();
        postElastice2.setId(2);
        postElastice2.setTitle("The man got the jackpot of smartlott");
        postElastice2.setCategories(Arrays.asList(cat1));
        System.out.println(postElastice2.getCategories());
        postElasticService.update(postElastice2);

        PostElastic postElastic = postElasticService.getOne(2);
        System.out.println(postElastic);
    }

}