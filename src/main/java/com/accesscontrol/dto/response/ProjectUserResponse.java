package com.accesscontrol.dto.response;

import java.util.UUID;

import com.accesscontrol.enums.UserType;
import com.accesscontrol.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectUserResponse {
 
    private UUID userId;
    private String fullName;
    private String username;
    private UserType userType;

    
    public ProjectUserResponse(User user, UserType type) {
        this.userId = user.getId();
        this.fullName = user.getFullname();
        this.username = user.getUsername();
        this.userType = type;
    }

}
