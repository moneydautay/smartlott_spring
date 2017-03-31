package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.MessageDTO;
import com.smartlott.backend.persistence.domain.backend.User;
import com.smartlott.backend.persistence.domain.elastic.UserElastic;
import com.smartlott.backend.service.I18NService;
import com.smartlott.backend.service.UserService;
import com.smartlott.backend.service.elasticsearch.UserElasticService;
import com.smartlott.enums.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by greenlucky on 3/29/17.
 */
@RestController
@RequestMapping(UserSearchRestController.API_USER_SEARCH_URL)
public class UserSearchRestController {

    public static final String API_USER_SEARCH_URL = "/api/user/search";

    @Autowired
    private UserElasticService userElasticService;

    @Autowired
    private UserService userService;

    private List<MessageDTO> messageDTOS;

    @Autowired
    private I18NService i18NService;

    @GetMapping("/all")
    public ResponseEntity<Object> searchAll(@RequestParam("q") String query, Pageable pageable, Locale locale) {
        Map<String, Object> responseData = new HashMap<>();
        messageDTOS = new ArrayList<>();

        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize());
        Page<UserElastic> userElastics = userElasticService.searchAll(query, pageRequest);

        List<Long> ids = new ArrayList<>();

        userElastics.getContent().forEach(ue -> ids.add(ue.getId()));


        List<User> users = userService.getUserByIds(ids);


        Page<User> usersPage = new PageImpl<User>(users, pageRequest, userElastics.getTotalPages());

        responseData.put("data", usersPage);
        if (userElastics.getTotalElements() == 0)
            messageDTOS.add(new MessageDTO(MessageType.WARNING, i18NService.getMessage("admin.member.search.not.found.text", query, locale)));
        else
            messageDTOS.add(new MessageDTO(MessageType.SUCCESS,
                    i18NService.getMessage("admin.member.search.found.text",
                    new Object[] {String.valueOf(userElastics.getTotalElements()), query}, locale)));
        responseData.put("messages", messageDTOS);
        return new ResponseEntity<Object>(responseData, HttpStatus.OK);
    }

}
