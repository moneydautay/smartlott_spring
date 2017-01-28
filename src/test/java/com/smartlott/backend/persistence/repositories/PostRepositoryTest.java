package com.smartlott.backend.persistence.repositories;

import com.smartlott.SmartlottApplication;
import com.smartlott.backend.persistence.domain.backend.Category;
import com.smartlott.backend.persistence.domain.backend.Post;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.PostService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by greenlucky on 1/27/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SmartlottApplication.class)
public class PostRepositoryTest {

    @Autowired
    private CategoryRepository categoryPostRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() throws Exception{
        Assert.assertNotNull(postService);
        Assert.assertNotNull(testName);
    }

    @Test
    public void createPost() throws Exception{

        User user = userRepository.findOne(Long.valueOf(1));

        Set<Category> categoryPosts = new HashSet<>();

        Category category = new Category();
        category.setTitle(testName.getMethodName());
        category.setDescription(testName.getMethodName());

        //category = categoryPostRepository.save(category);

        categoryPosts.add(category);

        //create post
        Post post = new Post();

        post.setTitle(testName.getMethodName());
        post.setSlug(testName.getMethodName());
        post.setCategories(categoryPosts);
        post.setPostDate(LocalDateTime.now(Clock.systemDefaultZone()));
        post.setUser(user);

        post = postService.create(post);

        Assert.assertNotNull(post.getId());

        System.out.println(post);

        Category aspectCategory = categoryPostRepository.findOne(1);
        System.out.println(aspectCategory);
    }

    @Test
    public void testGetPostByCategory() throws Exception{
        User user = userRepository.findOne(Long.valueOf(1));

        Set<Category> categoryPosts = new HashSet<>();

        Category category = new Category();
        category.setTitle(testName.getMethodName());
        category.setDescription(testName.getMethodName());

        //category = categoryPostRepository.save(category);

        categoryPosts.add(category);

        //create post
        Post post = new Post();

        post.setTitle(testName.getMethodName());
        post.setSlug(testName.getMethodName());
        post.setCategories(categoryPosts);
        post.setPostDate(LocalDateTime.now(Clock.systemDefaultZone()));
        post.setUser(user);

        post = postService.create(post);

        Assert.assertNotNull(post.getId());

        PageRequest pageRequest = new PageRequest(1,10);
    }

}