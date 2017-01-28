package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    List<Category> findAll();

    Page<Category> findAll(Pageable pageable);
}
