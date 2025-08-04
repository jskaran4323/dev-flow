package com.accesscontrol.mapper;

import com.accesscontrol.dto.response.ProjectResponse;
import com.accesscontrol.models.Project;

public class ProjectMapper {
    public static ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .ownerUsername(project.getOwner().getUsername())
            .build();
    }
}
