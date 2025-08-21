package com.accesscontrol.dto.response;

import java.util.UUID;

import com.accesscontrol.enums.ProjectStatusType;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private UUID id;
    private String name;
    private ProjectStatusType status;
    private String description;
    private ProjectUserResponse owner; 
   
    
}