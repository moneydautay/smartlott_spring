package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface PostRepository extends CrudRepository<Post, Long>{

    public List<Post> findAll();

    public Page<Post> findAll(Pageable pageable);
}
