package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 16/12/2016.
 */
@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long>{

    /**
     * Deletes role by User if exist or nothing if not not
     * @param user
     */
    public void deleteByUser(User user);


    /**
     * Find all user role
     * @return
     */
    public List<UserRole> findAll();

    /**
     * Finy User Role by role
     * @param role given by user
     * @return A List of UserRole
     */
    public List<UserRole> findByRole(Role role);
}
