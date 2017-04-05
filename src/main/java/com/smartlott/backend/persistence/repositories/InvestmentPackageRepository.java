package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.InvestmentPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Repository
public interface InvestmentPackageRepository extends PagingAndSortingRepository<InvestmentPackage, Integer> {

    Optional<InvestmentPackage> findOne(int id);

    List<InvestmentPackage> findAll();

    Page<InvestmentPackage> findAll(Pageable pageable);

    List<InvestmentPackage> findByEnabled(boolean enabled);

    Page<InvestmentPackage> findByEnabled(boolean enabled, Pageable pageable);

    @Modifying
    @Query("update InvestmentPackage ip set ip.enabled = :status where ip.id = :id")
    int changeStatus(@Param("id") int id,@Param("status") boolean status);
}
