package com.smartlott.backend.service;

import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.persistence.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
@Service
@Transactional(readOnly = true)
public class RoleService {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);
    
    @Autowired
    private RoleRepository roleRepository;


    /**
     * Find all role
     * @return A list of roles
     */
    public List<Role> findAll(){
        return roleRepository.findAll();
    }


    /**
     * Create roles
     * @param roles a list of roles
     * @throws Exception
     */
    public void createRoles(List<Role> roles) throws Exception{
        for ( Role role: roles) {
            createRole(role);
        }
    }


    /**
     * Create a role
     * @param role
     * @throws Exception
     */
    public void createRole(Role role) throws Exception{
        LOGGER.debug("Create new role {}", role);
        roleRepository.save(role);
    }


}
