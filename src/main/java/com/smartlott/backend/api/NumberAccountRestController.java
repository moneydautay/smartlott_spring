package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.NumberAccount;
import com.smartlott.backend.persistence.domain.backend.SecurityToken;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.dto.NumberAccountDTO;
import com.smartlott.backend.service.*;
import com.smartlott.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by greenlucky on 12/24/16.
 */
@RestController
@RequestMapping(NumberAccountRestController.NUMBER_ACCOUNT_URL)
public class NumberAccountRestController {

    /** The application logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberAccountRestController.class);

    public static final String NUMBER_ACCOUNT_URL = "/api/number-account";

    @Autowired
    private NumberAccountService numberAccountService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PerfectMoneyService perfectMoneyService;

    @Value("${perfectmoney.id}")
    private String id;

    @Value("${perfectmoney.passPhrase}")
    private String passPhrase;

    @Autowired
    private SecurityTokenSevice securityTokenSevice;

    @Autowired
    private NotificationService notificationService;


    /**
     * Get accounts by userid
     * @param userId
     * @return A list of number accounts or null if not found
     */
    @RequestMapping(value = "/by-user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAccounts(@PathVariable long userId){

        List<NumberAccount> numberAccounts = numberAccountService.findByUsername(userId);

        //create number account dto
        List<NumberAccountDTO> numberAccountDTOS = new ArrayList<>();

        numberAccounts.forEach( na -> numberAccountDTOS.add(new NumberAccountDTO(na)));

        return new ResponseEntity<Object>(numberAccountDTOS, HttpStatus.OK);
    }

    /**
     * Create new number account
     * @param userId
     * @param numberAccount
     * @param locale
     * @return A number account after createSecurityTokenForUsername
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@PathVariable long userId,@Valid @RequestBody NumberAccount numberAccount, Locale locale){

        List<MessageDTO> messageDTOS = new ArrayList<>();
        SecurityToken securityToken = securityTokenSevice.checkValidToken(numberAccount.getSecurityToken(), messageDTOS, locale);
        if(securityToken==null)
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);

        //Checking user exist
        User localUser = userService.findOne(userId);

        if(localUser == null){
            LOGGER.error("User id {} not found", userId);
            String message = messageSource.getMessage("Id.user.not.found", new Object[]{userId} , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        numberAccount.setUser(localUser);

        //Create new number account
        numberAccountService.create(numberAccount);

        //delete security token
        securityTokenSevice.remove(securityToken.getId());
        LOGGER.debug("Removed security token {} ",numberAccount.getSecurityToken());

        //turnoff notification
        if(!localUser.isActived() && localUser.getDocumentOne() != null && localUser.getDocumentTwo() != null)
            notificationService.turnOffNotification(localUser, NUMBER_ACCOUNT_URL);

        return new ResponseEntity<Object>(numberAccount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{numberAccountId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long numberAccountId, @Valid @RequestBody NumberAccount numberAccount, Locale locale){

        List<MessageDTO> messageDTOS = new ArrayList<>();

        NumberAccount localNumberAccount = numberAccountService.getAccount(numberAccountId);

        if(localNumberAccount == null){
            LOGGER.error("Number account id {} not found", numberAccountId);
            String message = messageSource.getMessage("NotFound.numberAccount.number", new Object[]{numberAccountId} , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        localNumberAccount.setNumber(numberAccount.getNumber());

        //Update new number account
        localNumberAccount = numberAccountService.update(localNumberAccount);

        return new ResponseEntity<Object>(localNumberAccount, HttpStatus.OK);
    }

    /**
     * Verify number account if valid to send email
     * @param numberAccount
     * @param locale
     * @return number account or null if not valid
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ResponseEntity<Object> verifyNumberAccount(@Valid @RequestBody NumberAccount numberAccount, Locale locale){
        System.out.println(numberAccount.toString());
        List<MessageDTO> messageDTOS = new ArrayList<>();

        if(numberAccount.getNumberAccountType().getId()==2) {
            String verifyResult = verifyAccountPerfectMoney(numberAccount.getNumber().trim()).trim();
            if (verifyResult.isEmpty() || verifyResult.equals("ERROR: Invalid Account")) {
                LOGGER.error("Number account {} not valid", numberAccount.getNumber());
                String message = messageSource.getMessage("NotValid.numberAccount.number", new Object[]{numberAccount.getNumber()} , locale);
                messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
                return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
            }
        }

        //checking duplicated number account
        NumberAccount localNumberAccount = numberAccountService.findByNumber(numberAccount.getNumber());
        if(localNumberAccount != null){
            LOGGER.error("Number account {} has been existed", numberAccount.getNumber());
            String message = messageSource.getMessage("Duplicated.numberAccount.number", new Object[]{numberAccount.getNumber()} , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        //send email
        securityTokenSevice.sendSecurityTokenEmail(numberAccount.getUser().getEmail(), locale);

        return new ResponseEntity<Object>(numberAccount, HttpStatus.OK);
    }

    @RequestMapping(value = "/verify-delete", method = RequestMethod.POST)
    public ResponseEntity<Object> verifyDeleteNumberAccount(@Valid @RequestBody NumberAccount numberAccount, Locale locale){
        //send email
        securityTokenSevice.sendSecurityTokenEmail(numberAccount.getUser().getEmail(), locale);

        return new ResponseEntity<Object>(numberAccount, HttpStatus.OK);
    }

    /**
     * Get number account given by number account id
     * @param numberAccountId
     * @param locale
     * @return A number account or null if not found
     */
    @RequestMapping(value = "/{numberAccountId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAccount(@PathVariable long numberAccountId, Locale locale){

        List<MessageDTO> messageDTOS = new ArrayList<>();

        NumberAccount numberAccount = numberAccountService.getAccount(numberAccountId);

        if(numberAccount == null){
            LOGGER.error("Number account id {} not found", numberAccountId);
            String message = messageSource.getMessage("NotFound.numberAccount.number", new Object[]{numberAccountId} , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<Object>(numberAccount, HttpStatus.OK);
    }

    @RequestMapping(value = "/{numberAccountId}/{token}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long numberAccountId, @PathVariable String token, Locale locale){
        List<MessageDTO> messageDTOS = new ArrayList<>();

        //check security token
        SecurityToken securityToken = securityTokenSevice.getSecurityTokenByToken(token);
        if(securityToken == null || LocalDateTime.now(Clock.systemDefaultZone()).isAfter(securityToken.getExpiryDate())){
            LOGGER.error("Token {} is not valid", token);
            String message = messageSource.getMessage("security.token.invalid", new Object[]{token} , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        NumberAccount numberAccount = numberAccountService.getAccount(numberAccountId);
        if(numberAccount == null){
            LOGGER.error("Number account id {} not found", numberAccountId);
            String message = messageSource.getMessage("NotFound.numberAccount.number", new Object[]{numberAccountId} , locale);
            messageDTOS.add(new MessageDTO(MessageType.ERROR,message));
            return new ResponseEntity<Object>(messageDTOS, HttpStatus.EXPECTATION_FAILED);
        }

        numberAccountService.delete(numberAccount);

        return new ResponseEntity<Object>(numberAccount, HttpStatus.OK);
    }

    /**
     * Verify account exist perfect money
     * @param account
     * @return username of or null if not found
     */
    public String verifyAccountPerfectMoney(String account){
        String result = null;
        result = perfectMoneyService.verifyAccount(id, passPhrase, account);
        System.out.println(result);
        LOGGER.info("Checking account perfect money: {}", result);
        return result;
    }


    @GetMapping("/change-status")
    public ResponseEntity<Object> active(@RequestParam("ids") List<Long> ids, boolean status, Locale locale) {
        int numberChange = 0;
        if(status == true)
            numberChange = numberAccountService.activeNumberAccount(ids);
        else
            numberChange = numberAccountService.inActiveNumberAccount(ids);

        return new ResponseEntity<Object>(numberChange, HttpStatus.OK);
    }



    /**
     * Convert list number account to number account DTO.
     *
     * @param numberAccounts
     * @return a list of Number account DTO or null if number account is null
     */
    private List<NumberAccountDTO> converToDTO(List<NumberAccount> numberAccounts) {
        List<NumberAccountDTO>  numberAccountDTOS = new ArrayList<>();
        numberAccounts.forEach(na -> numberAccountDTOS.add(new NumberAccountDTO(na)));
        return numberAccountDTOS;
    }


}
