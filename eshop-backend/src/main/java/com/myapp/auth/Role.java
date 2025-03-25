package com.myapp.auth;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN, CUSTOMER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
