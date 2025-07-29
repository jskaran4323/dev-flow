package com.accesscontrol.dto.request;

import java.util.UUID;

import com.accesscontrol.enums.UserType;

import lombok.Data;
@Data
public class AddTeamMemberRequest {
    private UUID userId;
    private UserType userType;

}
