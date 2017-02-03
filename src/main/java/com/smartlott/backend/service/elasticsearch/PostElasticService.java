package com.smartlott.backend.service.elasticsearch;

import com.smartlott.backend.persistence.domain.backend.Post;
import com.smartlott.backend.persistence.domain.elastic.PostElastic;
import com.smartlott.backend.persistence.repositories.elasticsearch.PostElasticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by greenlucky on 2/3/17.
 */
@Service
public class PostElasticService {
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(PostElasticService.class);

    @Autowired
    private PostElasticRepository postElasticRepository;

    public Page<PostElastic> searchByTitle(String title, Pageable pageable){
        return postElasticRepository.findByTitle(title, pageable);
    }

    public Page<PostElastic> searchByTitleAndCategoriesId(String title, int categoryId,Pageable pageable){
        return postElasticRepository.findByTitleAndCategoriesId(title, categoryId,pageable);
    }



    public PostElastic create(Post post) {
        //add post to elastic search
        PostElastic postElastic = new PostElastic();
        postElastic.setId(post.getId());
        postElastic.setTitle(post.getTitle());
        postElastic.setPublished(post.isStatus());
        postElastic.setCategories(post.getCategories());

        return postElasticRepository.save(postElastic);
    }

    public PostElastic update(Post post) {

        PostElastic postElastic = postElasticRepository.findOne(post.getId());

        //if postElastic not exist create new
        if(postElastic == null){
            return create(post);
        }

        postElastic.setTitle(post.getTitle());
        postElastic.setCategories(post.getCategories());
        postElastic.setPublished(post.isStatus());
        System.out.println(postElastic);
        return postElasticRepository.save(postElastic);
    }

    public void delete(long id) {
        postElasticRepository.delete(id);
    }

    public PostElastic getOne(long id) {
        return postElasticRepository.findOne(id);
    }
}
