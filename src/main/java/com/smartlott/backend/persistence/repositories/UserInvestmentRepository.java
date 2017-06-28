package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.UserInvestment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 09/02/2017.
 */
@Repository
public interface UserInvestmentRepository extends PagingAndSortingRepository<UserInvestment, Long> {

    @Modifying
    @Query("update UserInvestment ui  set  ui.enabled = :status where ui.user.id = :userId and (ui.fromDate <= :now and  ui.toDate >= :now)")
    int disabledInvestmentPackage(@Param("userId") long userId, @Param("now") LocalDateTime now, @Param("status") boolean status);

    List<UserInvestment> findByInvestmentPackageIncomeComponentIdAndFromDateLessThanAndToDateGreaterThanAndEnabled(long id, LocalDateTime fromdate, LocalDateTime toDate, boolean enabled);
}
