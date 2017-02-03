package com.smartlott.backend.persistence.elasticsearch;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by greenlucky on 1/30/17.
 */
@NoRepositoryBean
public interface ElasticsearchCrudRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}
