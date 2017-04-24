package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.RolesEnum;
import com.smartlott.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 4/21/17.
 */
@RestController
@RequestMapping(StaffHandler.API_STAFF)
public class StaffHandler {

    public static final String API_STAFF = "/api/staff";
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(StaffHandler.class);

    private static  final List<String> USER_ROLES = Arrays.asList(
            RolesEnum.SEO.getName(),
            RolesEnum.MANAGER.getName(),
            RolesEnum.ADMIN.getName(),
            RolesEnum.STAFF.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private I18NService i18NService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(Pageable pageable) {
        Page<User> users = userService.getByRoles(USER_ROLES, pageable);
        return new ResponseEntity<Object>(users, HttpStatus.OK);
    }

    /**
     * Get user by username.
     *
     * @param userId given by user
     * @param locale current locale given by browser
     * @return A user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable long userId, Locale locale) {

        User user = userService.findOneByIdAndRoleNamesIn(userId, USER_ROLES);

        if (user == null) {
            LOGGER.error("UserId {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

}
