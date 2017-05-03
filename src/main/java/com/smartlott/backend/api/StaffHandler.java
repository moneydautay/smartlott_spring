package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.Network;
import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.RolesEnum;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

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
    private static  final String CUSTOMER_ROLE = RolesEnum.CUSTOMER.getName();


    @Autowired
    private UserService userService;

    @Autowired
    private I18NService i18NService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(Pageable pageable) {
        Page<User> users = userService.getRolesNot(CUSTOMER_ROLE, pageable);
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

        User user = userService.findOne(userId);

        if (user == null || (user.getRoles().contains(new Role(RolesEnum.CUSTOMER)) && user.getRoles().size()==1)) {
            LOGGER.error("UserId {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    @PutMapping("/update-roles/{userId}")
    public ResponseEntity<Object> updateRoles(@PathVariable long userId, @RequestBody Set<Role> roles, Locale locale) {
        User user = userService.findOne(userId);
        if (user == null) {
            LOGGER.error("UserId {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }

        user.setRoles(roles);

        user = userService.updateUser(user);
        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS, i18NService.getMessage("admin.user.success.update.role.text", user.getUsername(), locale));

        return new ResponseEntity<Object>(Arrays.asList(messageDTO), HttpStatus.OK);
    }

    /**
     * Create a new user.
     *
     * @param user
     * @param locale current locale get by browser
     * @return A User or error if email or username is not valid
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, Locale locale) {

        List<MessageDTO> messages = new ArrayList<>();
        List<Network> networks = new ArrayList<>();

        boolean duplicated = false;

        if (userService.findByEmail(user.getEmail()) != null) {
            LOGGER.error("Email {} not valid", user.getEmail());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("NotValid.user.email", user.getEmail(), locale)));
            duplicated = true;
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            LOGGER.error("Username {} not valid", user.getUsername());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("NotValid.user.username", user.getUsername(), locale)));
            duplicated = true;
        }

        if (duplicated) {
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        user = userService.createStaff(user);

        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.user.success.created.text", user.getUsername(), locale));
        Map<String, Object> response = ResponseUtil.Response(messageDTO, user);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
