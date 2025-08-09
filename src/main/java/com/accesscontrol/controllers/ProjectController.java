package com.accesscontrol.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.config.CustomUserDetails;
import com.accesscontrol.dto.response.ProjectResponse;
import com.accesscontrol.mapper.ProjectMapper;
import com.accesscontrol.models.Project;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.ProjectRepository;
import com.accesscontrol.services.ProjectService;
import com.accesscontrol.services.ProjectUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {

  @Autowired
  private ProjectService projectService;
 @Autowired
 private ProjectUserService projectUserService;
  @Autowired
  private ProjectRepository projectRepository;
  @GetMapping("/all-projects")
  public ResponseEntity<List<ProjectResponse>> getAllProjects() {
    List<Project> allProjects = projectRepository.findAll();
    List<ProjectResponse> response = allProjects.stream()
        .map(ProjectMapper::toResponse)
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }

  @PostMapping("/project/register")
  public ResponseEntity<ProjectResponse> createProject(@RequestBody Project project, Authentication auth) {
    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    User user = userDetails.getUser();
    project.setOwner(user);
    Project savedProject = projectService.createProject(project);
    return ResponseEntity.ok(ProjectMapper.toResponse(savedProject));
  }

  @GetMapping("/projects")
  public ResponseEntity<List<ProjectResponse>> getUserProjects(Authentication auth) {
    CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    User user = userDetails.getUser();
    List<Project> projects = projectService.getProjectsByOwnerId(user.getId());

    List<ProjectResponse> response = projects.stream()
        .map(ProjectMapper::toResponse)
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/project/{id}")
  public ResponseEntity<ProjectResponse> getProject(@PathVariable UUID id) {
    return projectService.getProjectById(id)
        .map(project -> ResponseEntity.ok(ProjectMapper.toResponse(project)))
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/project/delete/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
    projectService.deleteProject(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/projects/{projectId}")
public ResponseEntity<ProjectResponse> getProjectDetails(
    @PathVariable UUID projectId,
    Authentication authentication
) {
  CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
  UUID currentUserId = currentUser.getUser().getId();
  

    if (!projectUserService.isOwnerOrMember(projectId, currentUserId)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    Optional<Project> projectOpt = projectRepository.findById(projectId);
    if (projectOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(ProjectMapper.toResponse(projectOpt.get()));
    
}

}
