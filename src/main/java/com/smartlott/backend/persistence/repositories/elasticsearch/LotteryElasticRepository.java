package com.smartlott.backend.persistence.repositories.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.LotteryElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by greenlucky on 4/19/17.
 */
@Repository
public interface LotteryElasticRepository extends ElasticsearchRepository<LotteryElastic, Long> {


    Page<LotteryElastic> findAll(Pageable pageable);

}
