package com.accesscontrol.enums;
import java.util.*;
public enum IssueType {
    OPEN(0),
    CLOSED(1),
    IN_PROGRESS(2);

    private final int value;
     
    IssueType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
    
    public static IssueType fromValue(int value) {
        return Arrays.stream(IssueType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid IssueType: " + value));
    }
}
