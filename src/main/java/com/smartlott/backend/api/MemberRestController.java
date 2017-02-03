package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.UserService;
import com.smartlott.utils.PageRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * Created by greenlucky on 2/3/17.
 */
@RestController
@RequestMapping(MemberRestController.API_MEMBER_URL)
public class MemberRestController {

    public static final String API_MEMBER_URL = "/api/member";

    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";

    @Autowired
    private UserService userService;

    @Autowired
    private I18NService i18NService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<Object> getAllMember(Pageable pageable, Locale locale){
        Page<User> users = userService.getByRole(ROLE_CUSTOMER,PageRequestUtils.createPageRequest(pageable));
        return new ResponseEntity<Object>(users, HttpStatus.OK);
    }
}
