package com.smartlott.backend.api;

import com.smartlott.backend.persistence.domain.backend.Role;
import com.smartlott.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by greenlucky on 4/24/17.
 */
@RestController
@RequestMapping(RoleHandler.API_ROLE)
public class RoleHandler {

    public static final String API_ROLE = "/api/role";

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRole() {
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<Object>(roles, HttpStatus.OK);
    }
}
