package com.accesscontrol.enums;

public enum InventoryType {
    ELECTRONICS(0),
    FURNITURE(1),
    FOOD(2),
    CLOTHING(3),
    OFFICE_SUPPLIES(4);


    private final int value;

    InventoryType(int value){
         this.value = value;

    }

    public int getValue(){
        return value;
       
    }
    public static InventoryType fromValue(int value){
        for(InventoryType type: values()){
            if(type.value == value) return type;
        }
        throw new IllegalArgumentException("Invalid inventory type: " + value);
    }
}
