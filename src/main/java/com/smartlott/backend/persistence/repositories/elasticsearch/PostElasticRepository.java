package com.smartlott.backend.persistence.repositories.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.PostElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greenlucky on 2/3/17.
 */
@Repository
public interface PostElasticRepository extends ElasticsearchRepository<PostElastic, Long>{

    Page<PostElastic> findByTitle(String title, Pageable pageable);

    Page<PostElastic> findByTitleAndCategoriesId(String title, int categoryId, Pageable pageable);
}
