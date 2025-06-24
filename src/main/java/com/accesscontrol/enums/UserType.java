package com.accesscontrol.enums;
import java.util.*;
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

    public String toSpringRole() {
        return "ROLE_" + this.name(); 
    }
}


