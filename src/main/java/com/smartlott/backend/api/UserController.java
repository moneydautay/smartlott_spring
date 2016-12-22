package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.backend.UserRole;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.RoleService;
import com.smartlott.backend.service.UserRoleService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.RolesEnum;
import com.smartlott.exceptions.RoleNotFoundException;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


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

    @Autowired
    private I18NService i18NService;


    /**
     * Get numeric members in system has role given by roleId
     * @param roleId
     * @return A numeric members
     */
    @RequestMapping(value = "/numeric-joined-member/{roleId}", method = RequestMethod.GET)
    public Long getNumericJoinedMemember(@PathVariable int roleId){
        Role role = roleService.getRole(roleId);
        if(role == null){
            throw  new RoleNotFoundException(roleId);
        }
        return (long) userRoleService.findByRole(role).size();
    }


    /**
     * Create a new user
     * @param user
     * @param locale current locale get by browser
     * @return A User or error if email or username is not valid
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, Locale locale){

        List<MessageDTO> messages = new ArrayList<>();
        boolean duplicated = false;


        if(userService.findByEmail(user.getEmail()) != null){
            LOGGER.error("Email {} not valid", user.getEmail());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("NotValid.user.email", user.getEmail(),locale)));
            duplicated = true;
        }

        if(userService.findByUsername(user.getUsername()) != null) {
            LOGGER.error("Username {} not valid", user.getUsername());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("NotValid.user.username", user.getUsername() ,locale)));
            duplicated = true;
        }

        if(duplicated){
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(new Role(RolesEnum.CUSTOMER),user));
        user.setUserRoles(userRoles);
        userService.createUser(user);

        //Auto login the registered user
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        LOGGER.info("User {} has been created and logged to application ", user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, Locale locale){
        if(userService.findOne(user.getId()) == null){
            LOGGER.error("User {} not found", user);
            return new ResponseEntity<Object>(i18NService.getMessage("Id.user.not.found", String.valueOf(user.getId()),locale), HttpStatus.BAD_REQUEST);
        }

        user = userService.updateUser(user);

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    /**
     * Get user by username
     * @param username given by user
     * @param locale current locale given by browser
     * @return A user
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(@PathVariable String username, Locale locale){

        User user = userService.findByUsername(username);

        if(user == null){
            LOGGER.error("Username {} was not found",username);
            return new ResponseEntity<Object>(new Object[]{i18NService.getMessage("Username.not.found", username, locale)}, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }


}
