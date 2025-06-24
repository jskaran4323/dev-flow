package com.accesscontrol.enums;
import java.util.*;
public enum LabelType {
    BUG(0),             // A problem or defect in the system
    FEATURE(1),         // A new feature request
    ENHANCEMENT(2),     // Improvement to an existing feature
    DOCUMENTATION(3),   // Docs-related task
    URGENT(4),          // Needs immediate attention
    DESIGN(5),          // UI/UX related
    TEST(6),            // QA or testing-related
    PERFORMANCE(7),     // Performance optimization
    SECURITY(8),        // Security-related issue
    BACKEND(9),         // Server-side work
    FRONTEND(10);



    private final int value;
     
    LabelType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
    
    public static LabelType fromValue(int value) {
        return Arrays.stream(LabelType.values())
                .filter(type -> type.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid LabelType: " + value));
    }

}
