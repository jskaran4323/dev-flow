package com.accesscontrol.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.request.JoinRequestDto;
import com.accesscontrol.dto.response.ProjectUserResponse;
import com.accesscontrol.models.Project;
import com.accesscontrol.models.ProjectJoinRequest;
import com.accesscontrol.models.ProjectUser;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.JoinRequestRepository;
import com.accesscontrol.repositories.ProjectRepository;
import com.accesscontrol.repositories.ProjectUserRepository;
import com.accesscontrol.repositories.UserRepository;
import com.accesscontrol.enums.*;
import com.accesscontrol.services.ProjectUserService;

@Service
public class ProjectUserServiceImpl implements ProjectUserService{

   @Autowired
private JoinRequestRepository joinRequestRepository;

@Autowired
private ProjectRepository projectRepository;

@Autowired
private ProjectUserRepository projectUserRepository;

@Autowired
private UserRepository userRepository;


    

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
                .userType(request.getUserType()) 
                .build();

        projectUserRepository.save(pu);
    }

    public List<ProjectUserResponse> getTeam(UUID projectId) {
        List<ProjectUser> team = projectUserRepository.findByProjectId(projectId);

        return team.stream()
                .map(pu -> new ProjectUserResponse(pu.getUser(), pu.getUserType()))
                .collect(Collectors.toList());
    }
    

    
    @Override
    public void requestToJoin(UUID projectId, UUID userId) {
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));
    
       
        if (project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("You are the project owner");
        }
    
        
        if (projectUserRepository.findByProjectIdAndUserId(projectId, userId).isPresent()) {
            throw new RuntimeException("You are already a team member of this project");
        }
    
        
        Optional<ProjectJoinRequest> existing = joinRequestRepository.findByProjectIdAndUserId(projectId, userId);
        if (existing.isPresent() && existing.get().getStatus().equals(JoinRequestStatus.PENDING.name())) {
            throw new RuntimeException("You already requested to join this project");
        }
    
        // ✅ Create new request
        ProjectJoinRequest request = ProjectJoinRequest.builder()
            .id(UUID.randomUUID())
            .projectId(projectId)
            .userId(userId)
            .status(JoinRequestStatus.PENDING.name())
            .createdAt(LocalDateTime.now())
            .build();
    
        joinRequestRepository.save(request);
    }
    


   @Override
public List<JoinRequestDto> getJoinRequests(UUID projectId, UUID ownerId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    if (!project.getOwner().getId().equals(ownerId)) {
        throw new RuntimeException("Only the project owner can view join requests");
    }

    List<ProjectJoinRequest> requests = joinRequestRepository.findByProjectIdAndStatus(projectId, JoinRequestStatus.PENDING.name());

    return requests.stream().map(request -> {
        User user = userRepository.findById(request.getUserId()).orElse(new User());
        JoinRequestDto dto = new JoinRequestDto();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullname());
        dto.setStatus(request.getStatus());
        dto.setCreatedAt(request.getCreatedAt());
        return dto;
    }).collect(Collectors.toList());
}


@Override
public void approveJoinRequest(UUID projectId, UUID userId, UUID ownerId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    if (!project.getOwner().getId().equals(ownerId)) {
        throw new RuntimeException("Only the project owner can approve join requests");
    }

    ProjectJoinRequest request = joinRequestRepository.findByProjectIdAndUserId(projectId, userId)
        .orElseThrow(() -> new RuntimeException("Join request not found"));

    if (!request.getStatus().equals(JoinRequestStatus.PENDING.name())) {
        throw new RuntimeException("Request already processed");
    }

    request.setStatus(JoinRequestStatus.APPROVED.name());
    joinRequestRepository.save(request);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    
    if (projectUserRepository.existsByProjectAndUser(project, user)) {
        throw new RuntimeException("User is already a team member");
    }

   
    ProjectUser projectUser = ProjectUser.builder()
        .project(project)
        .user(user)
        .userType(UserType.fromValue(user.getUserType()))
        .build();

    projectUserRepository.save(projectUser);
}

}

