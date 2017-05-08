package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Bonus;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public List<Bonus> findByOfUser(User ofUser);

    /**
     * Find all bonus
     * @return A list of bonus
     */
    public Page<Bonus> findAll(Pageable pageable);

    /**
     * Retrieve all bonus of user
     * @param ofUserId
     * @return A list of bonus of user or null if not exists
     */
    public List<Bonus> findByOfUserId(long ofUserId);

    /**
     * Retrieve all bonus of user
     * @param ofUserId
     * @return A list of bonus of user or null if not exists
     */
    public Page<Bonus> findByOfUserId(long ofUserId, Pageable pageable);

    /**
     * gets sum bonus of user in day given by user id and now
     * @param userId
     * @param fromDate
     * @param toDate
     * @return A sum of user bonus or zero if not exist
     */
    @Query("select COALESCE(sum(bonus),0) from Bonus as b " +
            "where b.ofUser.id = :userId and b.receivedDate between :fromDate and :toDate")
    double getSumBonusByUserId(@Param("userId") long userId,
                               @Param("fromDate") LocalDateTime fromDate,
                               @Param("toDate") LocalDateTime toDate);

    /**
     * Gets total bonus of user id
     * @param userId
     * @return A total bonus of user or zero if not exist
     */
    @Query("select COALESCE(sum(bonus),0) from Bonus as b where b.ofUser.id = :userId")
    double getTotalBonusByUserId(@Param("userId") long userId);
}
