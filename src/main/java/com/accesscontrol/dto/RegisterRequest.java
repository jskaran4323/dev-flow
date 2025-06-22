package com.accesscontrol.dto;

import lombok.Data;


import com.accesscontrol.enums.UserType;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private String fullName;
    private UserType type;
  
}
