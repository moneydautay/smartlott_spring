package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.service.RoleService;
import com.smartlott.backend.service.UserRoleService;
import com.smartlott.backend.service.UserService;
import com.smartlott.exceptions.RoleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Mrs Hoang on 20/12/2016.
 */
@RestController
@RequestMapping("/api/user")
public class User {

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/numeric-joined-member/{roleId}", method = RequestMethod.GET)
    public Long getNumericJoinedMemember(@PathVariable int roleId){
        Role role = roleService.getRole(roleId);
        if(role == null){
            throw  new RoleNotFoundException(roleId);
        }
        return (long) userRoleService.findByRole(role).size();
    }
}
