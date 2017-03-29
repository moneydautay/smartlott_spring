package com.smartlott.backend.persistence.repositories.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.UserElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by greenlucky on 3/29/17.
 */
@Repository
public interface UserElasticRepository extends ElasticsearchRepository<UserElastic, Long>{

    List<UserElastic> findAll();

    Page<UserElastic> findAll(Pageable pageable);

    Page<UserElastic> findByUsername(String username, Pageable pageable);

    Page<UserElastic> findByEmail(String email, Pageable pageable);

}
