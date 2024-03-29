package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.domain.backend.NumberAccountType;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Repository
@Transactional(readOnly=true)
public interface NumberAccountRepository extends CrudRepository<NumberAccount, Long>{


    /**
     * FInd A list of number account of User or null if not found
     * @param user
     * @return Returns A list of number account of User or null if not found
     */
    public List<NumberAccount> findByUser(User user);


    /**
     * Find a number account of user or null if not found
     * @param user
     * @param numberAccountType
     * @return Returns a number account of user or null if not found
     */
    public NumberAccount findByUserAndNumberAccountType(User user, NumberAccountType numberAccountType);

    /**
     * Find all number account by username
     * @param userId
     * @return A list of number account or null if not found
     */
    List<NumberAccount> findByUserId(long userId);

    /**
     * Find number account by number account
     * @param number
     * @return a number account or null if not found
     */
    NumberAccount findByNumber(String number);

    @Modifying
    @Query("update NumberAccount as na set na.enabled = :enabled where na.id in (:ids)")
    int changeStatus(@Param("ids") List<Long> ids, @Param("enabled") boolean enabled);
}
