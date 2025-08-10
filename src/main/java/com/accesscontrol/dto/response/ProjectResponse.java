package com.accesscontrol.dto.response;

import java.util.List;
import java.util.UUID;

import com.accesscontrol.models.ProjectUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private UUID id;
    private String name;
    private String description;
    private ProjectUserResponse owner; 
   
    
}