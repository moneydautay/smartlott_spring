package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserInvestment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Mrs Hoang on 07/02/2017.
 */
public interface UserInvestmentRepository extends PagingAndSortingRepository<UserInvestment, Long> {

    Page<UserInvestment> findByUserId(long userId, Pageable pageable);

    Page<UserInvestment> findByInvestmentPackageId(int investmentPackageId, Pageable pageable);

    Page<UserInvestment> findByUserIdAndInvestmentPackageId(long userId, int packageId, Pageable pageable);
}
