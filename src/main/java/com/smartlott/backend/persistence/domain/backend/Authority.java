package com.smartlott.backend.persistence.domain.backend;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Mrs Hoang on 18/12/2016.
 */
public class Authority implements GrantedAuthority {

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority(){
        return authority;
    }
}
