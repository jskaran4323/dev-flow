package com.accesscontrol.controllers;


import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.models.Project;
import com.accesscontrol.models.User;
import com.accesscontrol.services.ProjectService;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {
  @Autowired
  private ProjectService projectService;  
 

  @PostMapping("/register")
  public ResponseEntity<Project> createProject(@RequestBody Project project, Authentication auth){
    User user = (User) auth.getPrincipal();
    project.setOwner(user);
    Project savedProject = projectService.createProject(project);
    return ResponseEntity.ok(savedProject);
  }
  
  @GetMapping("/")
  public ResponseEntity<List<Project>> getUserProjects(Authentication auth){
    User user = (User) auth.getPrincipal(); 
    System.out.println(user.getUsername());
    List<Project> projects = projectService.getProjectsByOwnerId(user.getId());
    return ResponseEntity.ok(projects);
  }
  @GetMapping("/{id}")
  public ResponseEntity<Project> getProject(@PathVariable UUID id){
    return projectService.getProjectById(id)
    .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    
  }
  @PostMapping("/delete/{id}")
    public  ResponseEntity<Void> deleteProject(@PathVariable UUID id){
      projectService.deleteProject(id);
      return ResponseEntity.noContent().build();
    }
  }

