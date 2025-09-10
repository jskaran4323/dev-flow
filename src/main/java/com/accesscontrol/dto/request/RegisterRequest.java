package com.accesscontrol.dto.request;

import com.accesscontrol.enums.UserType;
import lombok.Data;

@Data
public class RegisterRequest {

  private String username;
  private String password;
  private String email;
  private String fullName;
  private UserType type;
}
