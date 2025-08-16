package com.accesscontrol.mapper;

import com.accesscontrol.dto.response.ProjectResponse;
import com.accesscontrol.dto.response.ProjectUserResponse;
import com.accesscontrol.models.Project;


public class ProjectMapper {
    public static ProjectResponse toResponse(Project project) {
        // Create ProjectUserResponse for the owner
        ProjectUserResponse ownerResponse = ProjectUserResponse.builder()
            .userId(project.getOwner().getId())
            .fullName(project.getOwner().getFullname())
            .username(project.getOwner().getUsername())
            .userType(project.getOwner().getUserTypeEnum())
            .build();

        return ProjectResponse.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .status(project.getProjectStatusEnum())
            .owner(ownerResponse) 
            .build();
    }
}