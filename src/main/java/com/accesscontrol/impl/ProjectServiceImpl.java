package com.accesscontrol.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import com.accesscontrol.models.Project;
import com.accesscontrol.repositories.ProjectRepository;
import com.accesscontrol.services.ProjectService;


import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository ;

    @Override
    public Project createProject(Project project) {
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        return projectRepository.save(project);

    }

    @Override
    public List<Project> getProjectsByOwnerId(UUID id) {
        return projectRepository.findByOwnerId(id);   
    }

    @Override
    public Optional<Project> getProjectById(UUID id) {
        return projectRepository.findById(id);
       
    }


    @Override
    public void deleteProject(UUID id) {
     projectRepository.deleteById(id);
    }
    
}
