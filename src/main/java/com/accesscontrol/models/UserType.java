package com.accesscontrol.models;

public enum UserType {
    ADMIN(0),
    STAFF(1),
    CUSTOMER(2);


    private final int value;

    UserType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;

}

    public static UserType fromValue(int value){
        for(UserType type: values()){
            if(type.value == value){
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid user type Value: "+value );
    }
}
