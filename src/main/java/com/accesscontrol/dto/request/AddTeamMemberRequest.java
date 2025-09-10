package com.accesscontrol.dto.request;

import com.accesscontrol.enums.UserType;
import java.util.UUID;
import lombok.Data;

@Data
public class AddTeamMemberRequest {
  private UUID userId;
  private UserType userType;
}
