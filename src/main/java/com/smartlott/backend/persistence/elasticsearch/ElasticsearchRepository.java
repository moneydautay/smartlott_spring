package com.smartlott.backend.persistence.elasticsearch;

import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.io.Serializable;

/**
 * Created by greenlucky on 1/30/17.
 */
public interface ElasticsearchRepository<T, ID extends Serializable> extends ElasticsearchCrudRepository<T, ID> {

    <S extends T> S index(S entity);

    Iterable<T> search(QueryBuilder query);

    Page<T> search(QueryBuilder query, Pageable pageable);

    Page<T> search(SearchQuery searchQuery);

    Page<T> searchSimilar(T entity, String[] fields, Pageable pageable);

    void refresh();

    Class<T> getEntityClass();
}
