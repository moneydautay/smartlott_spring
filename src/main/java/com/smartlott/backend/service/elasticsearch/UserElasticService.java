package com.smartlott.backend.service.elasticsearch;

import com.smartlott.backend.persistence.domain.elastic.UserElastic;
import com.smartlott.backend.persistence.repositories.elasticsearch.UserElasticRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by greenlucky on 3/29/17.
 */
@Service
public class UserElasticService {

    @Autowired
    private UserElasticRepository userElasticRepository;

    public List<UserElastic> searchAll(String search) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("_all", search));

        return (List<UserElastic>) userElasticRepository.search(queryBuilder);
    }

    public Page<UserElastic> searchAll(String search, Pageable pageable) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(QueryBuilders.matchQuery("_all", search));

        return userElasticRepository.search(queryBuilder, pageable);
    }

    public Page<UserElastic> searchAll(String search, boolean isCustomer, Pageable pageable) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(QueryBuilders.matchQuery("_all", search))
                .filter(QueryBuilders.matchQuery("customer", isCustomer));

        return userElasticRepository.search(queryBuilder, pageable);
    }
}
