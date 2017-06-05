package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.UserService;
import com.smartlott.enums.MessageType;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.utils.PageRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * Created by greenlucky on 2/3/17.
 */
@RestController
@RequestMapping(MemberRestController.API_MEMBER_URL)
public class MemberRestController {

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberRestController.class);

    public static final String API_MEMBER_URL = "/api/member";

    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

    @Autowired
    private UserService userService;

    @Autowired
    private I18NService i18NService;


    @GetMapping("/total")
    public ResponseEntity<Object> getTotalMembers() {
        long totalMembers = userService.getTotalByRole(ROLE_CUSTOMER);
        return new ResponseEntity<Object>(totalMembers, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllMember(Pageable pageable) {
        Page<User> users = userService.getByRole(ROLE_CUSTOMER, PageRequestUtils.createPageRequest(pageable));
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

        User user = userService.findOne(userId, ROLE_CUSTOMER);

        if (user == null) {
            LOGGER.error("UserId {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }
}
