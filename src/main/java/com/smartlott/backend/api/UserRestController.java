package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.*;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import com.smartlott.enums.NotificationTypeEnum;
import com.smartlott.enums.RolesEnum;
import com.smartlott.exceptions.RoleNotFoundException;
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
public class UserRestController {

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;
    
    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    public static final String API_USER_REST_URL="/api/user";

    public static final String API_USER_NUMERIC_JOINED_REST_URL= UserRestController.API_USER_REST_URL+"/numeric-joined-member";
    public static final String API_USER_PASSWORD_REST_URL= UserRestController.API_USER_REST_URL+"/change-password";
    public static final String API_USER_UPLOADOC_REST_URL= UserRestController.API_USER_REST_URL+"/uploaddoc";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

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
     * Get numeric members in system has role given by roleId
     * @param roleId
     * @return A numeric members
     */
    @RequestMapping(value = API_USER_NUMERIC_JOINED_REST_URL+"/{roleId}", method = RequestMethod.GET)
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
    @RequestMapping(value = API_USER_REST_URL, method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, Locale locale){

        List<MessageDTO> messages = new ArrayList<>();
        List<Network> networks = new ArrayList<>();

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

        //checking introduced key whether valid or not
        if(user.getIntroducedKey() != null){
            User introducedUser = userService.getUserByIntroducedKey(user.getIntroducedKey());
            if(introducedUser == null){
                LOGGER.error("Introduced key {} not valid", user.getIntroducedKey());
                messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("NotValid.user.introduce.key",locale)));
                duplicated = true;
            }
            user.setIntroducedBy(introducedUser);
        }

        if(duplicated){
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(new Role(RolesEnum.CUSTOMER),user));
        user.setUserRoles(userRoles);
        user = userService.createUser(user);

        //Auto login the registered user
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        LOGGER.info("User {} has been created and logged to application ", user);

        int currentLevel = 1;
        NetworkLevel networkLevel = networkLevelService.getOne(1);
        //add user to network of introduced
        networks.add(new Network(user, user.getIntroducedBy(),networkLevel));

        //find ancestor of ancestor user
        networks.addAll(networkService.findAncestor(user, user.getIntroducedBy(),(--level),(++currentLevel)));

        //create network of user
        networkService.createNetworks(networks);

        LOGGER.debug("Created networks {} for user", networks, user);

        //set notification for new user
        setNotification(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Update infomation for user
     * @param user
     * @param locale
     * @return New information of user or Message and HttpStatus error if not found
     */
    @RequestMapping(value = API_USER_REST_URL+"/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateUser(@Valid @RequestBody User user, Locale locale){
        if(userService.findOne(user.getId()) == null){
            LOGGER.error("User {} not found", user);
            return new ResponseEntity<Object>(i18NService.getMessage("Id.user.not.found", String.valueOf(user.getId()),locale), HttpStatus.BAD_REQUEST);
        }

        user = userService.updateUser(user);

        if(!user.isActived())
            notificationService.turnOffNotification(user, API_USER_REST_URL);
        LOGGER.info("Update information of user {}", user);

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }


    /**
     * Get user by username
     * @param username given by user
     * @param locale current locale given by browser
     * @return A user
     */
    @RequestMapping(value = API_USER_REST_URL+"/{username}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(@PathVariable String username, Locale locale){

        User user = userService.findByUsername(username);

        if(user == null){
            LOGGER.error("Username {} was not found",username);
            return new ResponseEntity<Object>(new Object[]{i18NService.getMessage("Username.not.found", username, locale)}, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    /**
     * Change password
     * @param userPassword
     * @param locale
     * @return
     */
    @RequestMapping(value = API_USER_PASSWORD_REST_URL, method = RequestMethod.POST)
    public ResponseEntity<Object> changePassword(@RequestBody UserPassword userPassword, Locale locale){

        List<MessageDTO> messages = new ArrayList<>();
        LOGGER.info("User Password {}", userPassword);

        User localUser =  userService.checkingPassword(userPassword.getUsername(), userPassword.getCurrentPassword());

        //Checking valid username and password
        if(localUser == null){
            LOGGER.error("Username {} and Password {} is not valid", userPassword.getUsername(), userPassword.getCurrentPassword());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("profile.password.not.valid", locale)));
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        //Checking used password
        if(passwordService.existedPassword(localUser, userPassword.getNewPassword())){
            LOGGER.error("Password {} was used", userPassword.getNewPassword());
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("profile.password.exist", locale)));
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        //change all old password to false
        passwordService.changeStatusOldPassword(localUser, false);

        //insert new password to password with enabled is true
        Password password = passwordService.createNewPassword(localUser ,userPassword.getNewPassword());

        userService.changePassword(userPassword.getUsername(), password.getPassword());

        //turnoff notification
        if(!localUser.isActived())
            notificationService.turnOffNotification(localUser, API_USER_PASSWORD_REST_URL);

        LOGGER.info("Changed password for user {}", localUser);

        return new ResponseEntity<Object>(new Object[]{new MessageDTO(MessageType.ERROR, i18NService.getMessage("profile.password.change.succed", locale))}, HttpStatus.OK);
    }


    @RequestMapping(value = API_USER_UPLOADOC_REST_URL, method = RequestMethod.POST)
    public ResponseEntity<Object> uploadDoc(@RequestParam("username") String username,
                                            @RequestParam("docType") int docType,
                                            @RequestParam("tempFile") MultipartFile tempFile, Locale locale){
        List<MessageDTO> messages = new ArrayList<>();
        User localUser = userService.findByUsername(username);

        if(localUser == null){
            LOGGER.error("Username {} was not found", username);
            messages.add(new MessageDTO(MessageType.ERROR, i18NService.getMessage("Username.not.found", username ,locale)));
            return new ResponseEntity<Object>(messages, HttpStatus.EXPECTATION_FAILED);
        }

        if(tempFile != null && !tempFile.isEmpty()){
            String profileImageUrl = s3Service.storeProfileImage(tempFile,"doc"+docType,username);
            if(profileImageUrl != null){
                if(docType==1)
                    localUser.setDocumentOne(profileImageUrl);
                if(docType==2)
                    localUser.setDocumentTwo(profileImageUrl);
            }else{
                LOGGER.error("There are a problem uploading the profile image to S3 server. "
                        +"The user's profile will be created without the image profile");
            }
        }
        //turnoff notification
        if(!localUser.isActived() && localUser.getDocumentOne() != null && localUser.getDocumentTwo() != null)
            notificationService.turnOffNotification(localUser, API_USER_UPLOADOC_REST_URL);

        userService.updateUser(localUser);
        LOGGER.debug("Update user {}", localUser);

        return new ResponseEntity<Object>(localUser, HttpStatus.OK);
    }

    public void setNotification(User user){
        NotificationType type1 = new NotificationType(NotificationTypeEnum.General);
        NotificationType type3 = new NotificationType(NotificationTypeEnum.AddressValidate);
        NotificationType type4 = new NotificationType(NotificationTypeEnum.NumberAccount);
        NotificationType type5 = new NotificationType(NotificationTypeEnum.Orther);

        //add notification after create new user;
        Notification notif1= new Notification();
        notif1.setContent("Welcome to Smartlott");
        notif1.setUser(user);
        notif1.setNotificationType(type5);

        notificationService.create(notif1);
        LOGGER.info("Add notification {} for user {}", notif1, user);

        Notification notif2= new Notification();
        notif2.setContent("Please update your information before buy lottery");
        notif2.setUser(user);
        notif2.setNotificationType(type1);

        notificationService.create(notif2);
        LOGGER.info("Add notification {} for user {}", notif2, user);

        Notification notif3= new Notification();
        notif3.setContent("Please upload your bill of bank or bill or electricity or water to varify your address");
        notif3.setUser(user);
        notif3.setNotificationType(type3);

        notificationService.create(notif3);
        LOGGER.info("Add notification {} for user {}", notif3, user);

        Notification notif4= new Notification();
        notif4.setContent("Please add your number account of bank to withdraw you reward of lottery");
        notif4.setUser(user);
        notif4.setNotificationType(type4);
        notificationService.create(notif4);
        LOGGER.info("Add notification {} for user {}", notif4, user);
    }
}
