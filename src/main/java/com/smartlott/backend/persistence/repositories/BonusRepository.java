package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Bonus;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
public interface BonusRepository extends PagingAndSortingRepository<Bonus, Long> {

    /**
     * Find a list bonous of user given by user or null if not found
     * @param ofUser
     * @return A lis bonous of user
     */
    List<Bonus> findByOfUser(User ofUser);

    /**
     * Find all bonus
     * @return A list of bonus
     */
    Page<Bonus> findAll(Pageable pageable);

    /**
     * Retrieve all bonus of user
     * @param ofUserId
     * @return A list of bonus of user or null if not exist
     */
    List<Bonus> findByOfUserId(long ofUserId);

    /**
     * Retrieve all bonus of user
     * @param ofUserId
     * @return A list of bonus of user or null if not exist
     */
    Page<Bonus> findByOfUserId(long ofUserId, Pageable pageable);
}
