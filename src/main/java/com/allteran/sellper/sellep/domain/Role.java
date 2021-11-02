package com.allteran.sellper.sellep.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, MANAGER, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
