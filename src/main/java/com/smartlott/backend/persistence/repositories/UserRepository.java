package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, Long>{

    /**
     *Returns a User given by a Username and null if not found
     * @param username
     * @return A User given a username and null if not found
     */
    public User findByUsername(String username);


    /**
     *Returns a User given by a Email and null if not found
     * @param email
     * @return A user given by a email and null if not found
     */
    public User findByEmail(String email);

    /**
     * Find user by username and password
     * @param username
     * @param password
     * @return A user or null if not exist
     */
    public User findByUsernameAndPassword(String username, String password);

    @Modifying
    @Transactional
    @Query("update User u set u.password = :newPassword where u.username= :username")
    void changePassword(@Param("username") String username,@Param("newPassword") String newPassword);

    /**
     * find user by introduced key
     * @param introducedKey
     * @return A user or null if not found
     */
    public User findByIntroducedKey(String introducedKey);

    @Transactional
    public User save(User user);

    @Transactional
    @Modifying
    @Query("update User u set u.cash = :cash where u.id = :userId")
    public void updateCash(@Param("userId") long userId, @Param("cash") double cash);

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Page<User> findByUserRolesRoleName(String role, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update User u set u.introducedKey = :introducedKey where  u.id = :userId")
    void updateIntroducedKey(@Param("userId") long userId,@Param("introducedKey") String introducedKey);

    List<User> findByIdIn(List<Long> ids);

    @Transactional
    @Modifying
    @Query("update User u set u.actived = true, u.activeBy = :byUser, u.activeDate = :activeDate where u.id = :userId")
    int active(@Param("userId") long userId, @Param("byUser") User byUser,
               @Param("activeDate") LocalDateTime activeDate);

    @Transactional
    @Modifying
    @Query("update User u set u.enabled = :status, u.changeStatusBy = :byUser, u.changeStatusDate = :changeDate where u.id = :userId")
    int changeStatus(@Param("status") boolean status, @Param("userId") long userId,
                     @Param("byUser") User byUser, @Param("changeDate") LocalDateTime changeDate);
}
