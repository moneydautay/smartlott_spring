package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.SaleOff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Repository
public interface SaleOffRepository extends PagingAndSortingRepository<SaleOff, Long> {

    Page<SaleOff> findAll(Pageable pageable);
}
