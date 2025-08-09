package com.accesscontrol.services;

import com.accesscontrol.dto.response.ProjectResponse;
import com.accesscontrol.models.Project;
import java.util.*;


public interface ProjectService {
 Project createProject(Project project);
 List<Project> getProjectsByOwnerId(UUID id);
 Optional<Project> getProjectById(UUID id);
 void deleteProject(UUID id);    
 List <ProjectResponse> getMyProjects(UUID userId);
}
