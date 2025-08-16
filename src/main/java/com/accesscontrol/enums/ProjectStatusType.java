package com.accesscontrol.enums;
import java.util.*;
public enum ProjectStatusType {
    COMPLETED(0),
    IN_PROGRESS(2),
    ACTIVE(1);

    private final int value;
     
    ProjectStatusType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
    
    public static ProjectStatusType fromValue(int value) {
        return Arrays.stream(ProjectStatusType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElse(null); 
    }

}
