package com.smartlott.backend.service.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.LotteryElastic;
import com.smartlott.backend.persistence.repositories.elasticsearch.LotteryElasticRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by greenlucky on 4/19/17.
 */
@Service
public class LotteryElasticService {

    @Autowired
    private LotteryElasticRepository elasticRepository;

    public Page<LotteryElastic> getAll(Pageable pageable) {
        return elasticRepository.findAll(pageable);
    }

    public Page<LotteryElastic> search(String query, Pageable pageable) {
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();

        queryBuilder.must(QueryBuilders.matchQuery("_all", query));

        return elasticRepository.search(queryBuilder, pageable);
    }
}
