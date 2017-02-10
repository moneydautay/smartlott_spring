package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.UserInvestment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Repository
public interface UserInvestmentRepository extends PagingAndSortingRepository<UserInvestment, Long> {


}
