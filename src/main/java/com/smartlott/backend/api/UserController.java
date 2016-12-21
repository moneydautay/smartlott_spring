package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserRole;
import com.smartlott.backend.service.RoleService;
import com.smartlott.backend.service.UserRoleService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.RolesEnum;
import com.smartlott.exceptions.RoleNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Mrs Hoang on 20/12/2016.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user){

        User userLocal = userService.findByEmail(user.getEmail());

        if(userLocal != null){
            LOGGER.error("Email {} not valid", user.getEmail());
            return new ResponseEntity<User>(user, HttpStatus.EXPECTATION_FAILED);
        }
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(new Role(RolesEnum.CUSTOMER),user));
        user.setUserRoles(userRoles);
        userService.createUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


}
