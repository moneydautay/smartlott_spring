package com.smartlott.intergation;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.CategoryHasPost;
import com.smartlott.backend.persistence.domain.backend.CategoryPost;
import com.smartlott.backend.persistence.domain.backend.Post;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.repositories.CategoryHasPostRepository;
import com.smartlott.backend.persistence.repositories.CategoryPostRepository;
import com.smartlott.backend.persistence.repositories.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class PostTest extends AbstractIntegrationTest{

    @Autowired
    private CategoryPostRepository categoryPostRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryHasPostRepository categoryHasPostRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void beforeTest() throws Exception{
        Assert.assertNotNull(categoryPostRepository);
        Assert.assertNotNull(postRepository);
    }

    @Test
    public void testPost() throws Exception{

        //create user
        User user = createCustUser(testName);

        //checking user is not null
        Assert.assertNotNull(user);

        //create category
        CategoryPost categoryPost1 = new CategoryPost("News lottery", "News a bout lottery");
        CategoryPost categoryPost2 = new CategoryPost("Social", "News about social");

        categoryPostRepository.save(categoryPost1);
        categoryPostRepository.save(categoryPost2);

        Set<CategoryPost> categoryPosts = new HashSet<>();
        categoryPosts.add(categoryPost1);
        categoryPosts.add(categoryPost2);

        //checking category is not null
        Assert.assertNotNull(categoryPost1);
        Assert.assertNotNull(categoryPost2);

        //Add post
        Post post = new Post();
        post.setTitle("A Way To Be Successful person");
        post.setSlug("a-way-to-be-successful-person");
        post.setContent("Today we will talk about how to be a successful person.");
        post.setPostDate(LocalDateTime.now(Clock.systemUTC()));
        post.setUser(user);

        postRepository.save(post);

        Assert.assertNotNull(post);

        //Add category has post
        CategoryHasPost categoryHasPost1 = new CategoryHasPost(post, categoryPost1);
        categoryHasPostRepository.save(categoryHasPost1);

        CategoryHasPost categoryHasPost2 = new CategoryHasPost(post, categoryPost2);
        categoryHasPostRepository.save(categoryHasPost2);


        List<CategoryHasPost> posts = categoryHasPostRepository.findByCategoryPost(categoryPost1);

        Assert.assertNotNull(posts);
    }

}
