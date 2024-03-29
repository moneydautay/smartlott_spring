package com.smartlott.backend.api;


import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.RolesEnum;
import com.smartlott.exceptions.NotFoundException;
import com.smartlott.exceptions.OccurException;
import com.smartlott.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.*;


/**
 * Created by Mrs Hoang on 20/12/2016.
 */
@RestController
public class UserHandler {

    /**
     * The Serial Version UID for Serializable classes
     */
    private static final long serialVersionUID = 1L;

    /**
     * The application logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserHandler.class);

    public static final String API_USER_REST_URL = "/api/user";

    public static final String API_USER_NUMERIC_JOINED_REST_URL = UserHandler.API_USER_REST_URL + "/numeric-joined-member";
    public static final String API_USER_PASSWORD_REST_URL = UserHandler.API_USER_REST_URL + "/change-password";
    public static final String API_USER_UPLOADOC_REST_URL = UserHandler.API_USER_REST_URL + "/uploaddoc";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NetworkService networkService;

    @Autowired
    private NetworkLevelService networkLevelService;

    //Get level of network
    @Value("${network.level}")
    private int level;

    /**
     * Get numeric members in system has role given by roleId.
     *
     * @param roleId
     * @return A numeric members
     */
    @RequestMapping(value = API_USER_NUMERIC_JOINED_REST_URL + "/{roleId}", method = RequestMethod.GET)
    public Long getNumericJoinedMember(@PathVariable int roleId) {

        return Long.valueOf(0);
    }

    /**
     * Create a new user.
     *
     * @param user
     * @param locale current locale get by browser
     * @return A User or error if email or username is not valid
     */
    @RequestMapping(value = API_USER_REST_URL, method = RequestMethod.POST)
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

        //checking introduced key whether valid or not
        if (user.getIntroducedKey() != null) {
            User introducedUser = userService.getUserByIntroducedKey(user.getIntroducedKey());
            if (introducedUser == null) {
                LOGGER.error("Introduced key {} not valid", user.getIntroducedKey());
                messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("NotValid.user.introduce.key", locale)));
                duplicated = true;
            }
            user.setIntroducedBy(introducedUser.getUsername());
        }

        if (duplicated) {
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(new Role(RolesEnum.CUSTOMER));
        user.setRoles(userRoles);
        user = userService.createUser(user);

        //Auto login the registered user
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        LOGGER.info("User {} has been created and logged to application ", user);

        //find ancestor of ancestor user
        networks.addAll(networkService.findAncestor(user, level, 1));

        //create network of user
        networkService.createNetworks(networks);

        LOGGER.debug("Created networks {} for user", networks, user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Update information for user.
     *
     * @param user
     * @param locale
     * @return New information of user or Message and HttpStatus error if not found
     */
    @RequestMapping(value = API_USER_REST_URL + "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, Locale locale) {
        User localUser = userService.findOne(user.getId());
        if (localUser == null) {
            LOGGER.error("User {} not found", user);
            return new ResponseEntity<Object>(i18NService.getMessage("Id.user.not.found", String.valueOf(user.getId()), locale), HttpStatus.BAD_REQUEST);
        }

        user.setRoles(localUser.getRoles());
        user = userService.updateUser(user);

        if (!user.isActived())
            notificationService.turnOffNotification(user, API_USER_REST_URL);
        LOGGER.info("Update information of user {}", user);
        MessageDTO messageDTO = new MessageDTO(MessageType.SUCCESS, i18NService.getMessage("admin.user.success.update.text", user.getUsername(), locale));
        Map<String, Object> response = ResponseUtil.Response(messageDTO, user);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    /**
     * Get user by username.
     *
     * @param username given by user
     * @param locale   current locale given by browser
     * @return A user
     */
    @RequestMapping(value = API_USER_REST_URL + "/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(@PathVariable String username, Locale locale) {

        User user = userService.findByUsername(username);

        if (user == null) {
            LOGGER.error("Username {} was not found", username);
            return new ResponseEntity<Object>(new Object[]{i18NService.getMessage("Username.not.found", username, locale)}, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    /**
     * Get user by username.
     *
     * @param userId given by user
     * @param locale current locale given by browser
     * @return A user
     */
    @RequestMapping(value = API_USER_REST_URL + "/id/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(@PathVariable long userId, Locale locale) {

        User user = userService.findOne(userId);

        if (user == null) {
            LOGGER.error("UserId {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    /**
     * Change password.
     *
     * @param userPassword
     * @param locale
     * @return
     */
    @RequestMapping(value = API_USER_PASSWORD_REST_URL, method = RequestMethod.POST)
    public ResponseEntity<Object> changePassword(@RequestBody UserPassword userPassword, Locale locale) {

        List<MessageDTO> messages = new ArrayList<>();
        LOGGER.info("User Password {}", userPassword);

        User localUser = userService.checkingPassword(userPassword.getUsername(), userPassword.getCurrentPassword());

        //Checking valid username and password
        if (localUser == null) {
            LOGGER.error("Username {} and Password {} is not valid", userPassword.getUsername(), userPassword.getCurrentPassword());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("profile.password.not.valid", locale)));
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        //Checking used password
        if (passwordService.existedPassword(localUser, userPassword.getNewPassword())) {
            LOGGER.error("Password {} was used", userPassword.getNewPassword());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("profile.password.exist", locale)));
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        //change all old password to false
        passwordService.changeStatusOldPassword(localUser, false);

        //insert new password to password with enabled is true
        Password password = passwordService.createNewPassword(localUser, userPassword.getNewPassword());

        userService.changePassword(userPassword.getUsername(), password.getPassword());

        //turnoff notification
        if (!localUser.isActived())
            notificationService.turnOffNotification(localUser, API_USER_PASSWORD_REST_URL);

        LOGGER.info("Changed password for user {}", localUser);

        return new ResponseEntity<Object>(new Object[]{new MessageDTO(MessageType.ERROR, i18NService.getMessage("profile.password.change.succed", locale))}, HttpStatus.OK);
    }


    @RequestMapping(value = API_USER_UPLOADOC_REST_URL, method = RequestMethod.POST)
    public ResponseEntity<Object> uploadDoc(@RequestParam("username") String username,
                                            @RequestParam("docType") int docType,
                                            @RequestParam("tempFile") MultipartFile tempFile, Locale locale) {
        List<MessageDTO> messages = new ArrayList<>();
        User localUser = userService.findByUsername(username);

        if (localUser == null) {
            LOGGER.error("Username {} was not found", username);
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("Username.not.found", username, locale)));
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        if (tempFile != null && !tempFile.isEmpty()) {
            String profileImageUrl = s3Service.storeProfileImage(tempFile, "doc" + docType, username);
            if (profileImageUrl != null) {
                if (docType == 1)
                    localUser.setDocumentOne(profileImageUrl);
                if (docType == 2)
                    localUser.setDocumentTwo(profileImageUrl);
            } else {
                LOGGER.error("There are a problem uploading the profile image to S3 server. "
                        + "The user's profile will be created without the image profile");
            }
        }

        userService.updateDocument(localUser.getId(), localUser.getDocumentOne(), docType);
        LOGGER.debug("Update user {}", localUser);

        return new ResponseEntity<Object>(localUser, HttpStatus.OK);
    }


    @GetMapping(API_USER_REST_URL + "/active/{userId}/{byUserId}")
    public ResponseEntity<Object> activeMember(@PathVariable long userId, @PathVariable long byUserId, Locale locale) {

        List<MessageDTO> messages = new ArrayList<>();
        User localUser = userService.findOne(userId);
        User byUser = userService.findOne(byUserId);

        if (localUser == null) {
            LOGGER.error("Id {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }

        if (byUser == null) {
            LOGGER.error("Id {} was not found", byUserId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("Id.user.not.found", String.valueOf(byUserId), locale));
            throw new NotFoundException(messageDTO);
        }

        if (userService.active(userId, byUser) == 0) {
            LOGGER.error("Active member {} was failed", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.member.error.active.text", String.valueOf(userId), locale));
            throw new OccurException(messageDTO);
        }

        messages.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.member.active.success.text", String.valueOf(byUserId), locale)));

        localUser = userService.findOne(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("messages", messages);
        data.put("data", localUser);

        return new ResponseEntity<Object>(data, HttpStatus.OK);
    }

    @GetMapping(API_USER_REST_URL + "/change-status/{status}/{userId}/{byUserId}")
    public ResponseEntity<Object> changeStatusMember(@PathVariable boolean status, @PathVariable long userId, Locale locale) {

        User logonUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<MessageDTO> messages = new ArrayList<>();
        User localUser = userService.findOne(userId);

        if (localUser == null) {
            LOGGER.error("Id {} was not found", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("Id.user.not.found", String.valueOf(userId), locale));
            throw new NotFoundException(messageDTO);
        }


        Object[] objs = new Object[] {String.valueOf(status), String.valueOf(userId)};
        if (userService.changeStatus(status, userId, logonUser.getUsername()) == 0) {
            LOGGER.error("Change status member {} was failed", userId);
            MessageDTO messageDTO = new MessageDTO(MessageType.ERROR,
                    i18NService.getMessage("admin.member.error.change.status.text",
                            objs, locale));
            throw new OccurException(messageDTO);
        }

        messages.add(new MessageDTO(MessageType.SUCCESS,
                i18NService.getMessage("admin.member.change.status.success.text", objs, locale)));

        localUser = userService.findOne(userId);

        Map<String, Object> data = new HashMap<>();
        data.put("messages", messages);
        data.put("data", localUser);

        return new ResponseEntity<Object>(data, HttpStatus.OK);
    }
}
