package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Password;
import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@Repository
@Transactional(readOnly = true)
public interface PasswordRepository extends CrudRepository<Password, Long>{


    /**
     * Returns Passowrd a given by username or null if not found
     * @param user a given by user
     * @return A passowrd a given by username or null if not found
     */
    List<Password> findByUserAndEnabled(User user, Boolean enabled);


    /**
     * Changes all password of user to enabled
     * @param userId a given by user
     * @param enabled a given by user
     */
    @Transactional
    @Modifying
    @Query("update Password p set p.enabled = :enabled where p.user.id= :userId")
    void changePasswordByUserIdAndEnabled(@Param("userId") long userId, @Param("enabled") boolean enabled);

    /**
     * Find list password by user
     * @param user
     * @return A list password or null if not exist
     */
    List<Password> findByUser(User user);

}
