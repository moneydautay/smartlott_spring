package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.UserCash;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by greenlucky on 2/4/17.
 */
@Repository
public interface UserCashRepository extends PagingAndSortingRepository<UserCash, Long>{
    UserCash findByUserIdAndCashId(long userId, int cashId);

    List<UserCash> save(List<UserCash> userCashes);
}
