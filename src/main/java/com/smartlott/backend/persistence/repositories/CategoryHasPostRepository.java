package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.CategoryHasPost;
import com.smartlott.backend.persistence.domain.backend.CategoryPost;
import com.smartlott.backend.persistence.domain.backend.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface CategoryHasPostRepository extends CrudRepository<CategoryHasPost, Long>{

    /**
     * Find A List Category Post given by Category post
     * @param categoryPost
     * @returnA List Category Post
     */
    public List<CategoryHasPost> findByCategoryPost(CategoryPost categoryPost);
}
