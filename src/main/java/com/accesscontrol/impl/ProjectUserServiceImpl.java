package com.accesscontrol.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.response.ProjectUserResponse;
import com.accesscontrol.models.Project;
import com.accesscontrol.models.ProjectUser;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.ProjectRepository;
import com.accesscontrol.repositories.ProjectUserRepository;
import com.accesscontrol.repositories.UserRepository;
import com.accesscontrol.services.ProjectUserService;

@Service
public class ProjectUserServiceImpl implements ProjectUserService{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectUserRepository projectUserRepository;

    public void addTeamMember(UUID projectId, AddTeamMemberRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        if (projectUserRepository.findByProjectIdAndUserId(projectId, user.getId()).isPresent()) {
            throw new RuntimeException("User already part of the project");
        }

        ProjectUser pu = ProjectUser.builder()
                .project(project)
                .user(user)
                .usertype(request.getUserType()) 
                .build();

        projectUserRepository.save(pu);
    }

    public List<ProjectUserResponse> getTeam(UUID projectId) {
        List<ProjectUser> team = projectUserRepository.findByProjectId(projectId);

        return team.stream()
                .map(pu -> new ProjectUserResponse(pu.getUser(), pu.getUsertype()))
                .collect(Collectors.toList());
    }
}

