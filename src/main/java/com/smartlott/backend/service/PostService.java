package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Post;
import com.smartlott.backend.persistence.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by greenlucky on 1/27/17.
 */
@Service
@Transactional(readOnly = true)
public class PostService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    /**
     *
     * @param post
     * @return
     */
    @Transactional
    public Post create(Post post){
        return postRepository.save(post);
    }

    /**
     * updates post
     * @param post
     * @return a post after update
     */
    @Transactional
    public Post update(Post post){
        return postRepository.save(post);
    }

    /**
     * Deletes post by postId or nothing if postId not found
     *
     * @param postId
     */
    @Transactional
    public void delete(long postId){
        postRepository.delete(postId);
    }

    /**
     * Gets a post by post id or null if not found
     * @param postId
     * @return A post or null if not found
     */
    public Post getOne(long postId){
        return postRepository.findOne(postId);
    }

    /**
     * Retrieves all post or null if not exist
     * @return a list of post
     */
    public List<Post> getAll(){
        return postRepository.findAll();
    }

    public Page<Post> getByCategoryId(int categoryId, Pageable pageable){
        return postRepository.findByCategories_Id(categoryId, pageable);
    }

    /**
     * Retrieves a page of al post or null of post
     * @param pageable
     * @return A page of all post
     */
    public Page<Post> getAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    /**
     * Gets A post by slug given by slug
     *
     * @param slug
     * @return a slug or null if not exist
     */
    public Post getBySlug(String slug){
        return postRepository.findBySlug(slug);
    }

    /**
     * Checks exist post given by post id
     * @param postId
     * @return True if post exists post id or null if not exists
     */
    public boolean exist(int postId) {
        return postRepository.exists(Long.valueOf(postId));
    }

    public boolean existSlug(String slug) {
        Post post = postRepository.findBySlug(slug);
        System.out.println(post);
        if(post != null)
            return true;
        return false;
    }

    @Transactional
    public int delete(List<Long> postIds){
        return postRepository.delete(postIds);
    }

    @Transactional
    public int changeStatus(List<Long> postIds, boolean status){
        return postRepository.changeStatus(postIds, status);
    }
}
