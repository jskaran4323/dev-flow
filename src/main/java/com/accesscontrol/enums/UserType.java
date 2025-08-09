package com.accesscontrol.enums;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
public enum UserType {
    ADMIN(0),
    MANAGER(1),
    DEVELOPER(2),
    TESTER(3),
    VIEWER(4);

    private final int value;

    UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserType fromValue(int value) {
        return Arrays.stream(UserType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid UserType: " + value));
    }

   
     public GrantedAuthority toSpringRole() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}


