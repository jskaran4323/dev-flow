package com.accesscontrol.controllers;


import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.dto.response.ProjectResponse;
import com.accesscontrol.mapper.ProjectMapper;
import com.accesscontrol.models.Project;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.ProjectRepository;
import com.accesscontrol.repositories.ProjectUserRepository;
import com.accesscontrol.services.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {
  @Autowired
  private ProjectService projectService;  
  
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
  public ResponseEntity<Project> createProject(@RequestBody Project project, Authentication auth){
    User user = (User) auth.getPrincipal();
    project.setOwner(user);
    Project savedProject = projectService.createProject(project);
    return ResponseEntity.ok(savedProject);
  }
  
  @GetMapping("/projects")
  public ResponseEntity<List<Project>> getUserProjects(Authentication auth){
    User user = (User) auth.getPrincipal(); 
    List<Project> projects = projectService.getProjectsByOwnerId(user.getId());
    return ResponseEntity.ok(projects);
  }
  @GetMapping("/project/{id}")
  public ResponseEntity<Project> getProject(@PathVariable UUID id){
    return projectService.getProjectById(id)
    .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    
  }
  @DeleteMapping("/project/delete/{id}")
    public  ResponseEntity<Void> deleteProject(@PathVariable UUID id){
      projectService.deleteProject(id);
      return ResponseEntity.noContent().build();
    }
  }

