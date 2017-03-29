package com.smartlott.backend.service.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.UserElastic;
import com.smartlott.backend.persistence.repositories.elasticsearch.UserElasticRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by greenlucky on 3/29/17.
 */
@Service
public class UserElasticService {

    @Autowired
    private UserElasticRepository userElasticRepository;

    public Page<UserElastic> searchAll(String search, Pageable pageable) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(QueryBuilders.matchQuery("_all", search));

        return userElasticRepository.search(queryBuilder, pageable);
    }
}
