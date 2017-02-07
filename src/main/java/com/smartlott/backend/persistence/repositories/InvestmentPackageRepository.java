package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
@Repository
public interface InvestmentPackageRepository extends PagingAndSortingRepository<InvestmentPackage, Integer> {

    List<InvestmentPackage> findAll();

    Page<InvestmentPackage> findAll(Pageable pageable);
}
