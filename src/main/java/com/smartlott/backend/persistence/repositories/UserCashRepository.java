package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.UserCash;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by greenlucky on 2/4/17.
 */
@Repository
public interface UserCashRepository extends PagingAndSortingRepository<UserCash, Long>{

    List<UserCash> findAll();

    UserCash findByUserIdAndCashIdAndEnabled(long userId, int cashId, boolean enabled);

    UserCash findByUserIdAndCash_Received(long userId, boolean received);

    List<UserCash> save(List<UserCash> userCashes);

    Set<UserCash> save(Set<UserCash> userCashes);

    List<UserCash> findByUserIdAndEnabled(long userId, boolean b);
}
