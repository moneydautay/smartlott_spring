package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    public List<Post> findAll();

    public Page<Post> findAll(Pageable pageable);

    public Page<Post> findByCategories_Id(int categoryId, Pageable pageable);

    Post findBySlug(String slug);

    @Modifying
    @Query("delete from Post as p where p.id in (:postIds)")
    public int delete(@Param("postIds") List<Long> postIds);

    @Modifying
    @Query("update Post as p set status = :status where p.id in (:postIds)")
    public int changeStatus(@Param("postIds") List<Long> postIds, @Param("status") boolean status);

}
