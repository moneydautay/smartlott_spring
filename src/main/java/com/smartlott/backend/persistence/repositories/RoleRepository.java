package com.smartlott.backend.persistence.repositories;

import com.smartlott.backend.persistence.domain.backend.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Mrs Hoang on 12/15/2016.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{

    /**
     *
     * @return
     */
    List<Role> findAll();

}
