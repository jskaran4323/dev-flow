package com.accesscontrol.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.request.JoinRequestDto;
import com.accesscontrol.dto.response.ProjectUserResponse;
import com.accesscontrol.enums.JoinRequestStatus;
import com.accesscontrol.enums.UserType;
import com.accesscontrol.models.*;
import com.accesscontrol.repositories.*;
import com.accesscontrol.services.ProjectUserService;

@Service
public class ProjectUserServiceImpl implements ProjectUserService {

    @Autowired private ProjectRepository projectRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProjectUserRepository projectUserRepository;
    @Autowired private JoinRequestRepository joinRequestRepository;

    @Override
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

    @Override
    public List<ProjectUserResponse> getTeam(UUID projectId) {
        return projectUserRepository.findByProjectId(projectId).stream()
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
            throw new RuntimeException("Already a team member");
        }

        if (joinRequestRepository.existsByProjectIdAndUserIdAndStatus(projectId, userId, JoinRequestStatus.PENDING.name())) {
            throw new RuntimeException("Request already pending");
        }

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
            throw new RuntimeException("Only owner can view join requests");
        }

        return joinRequestRepository.findByProjectIdAndStatus(projectId, JoinRequestStatus.PENDING.name())
                .stream().map(request -> {
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
            throw new RuntimeException("Only owner can approve");
        }

        ProjectJoinRequest request = joinRequestRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new RuntimeException("Join request not found"));

        if (!request.getStatus().equals(JoinRequestStatus.PENDING.name())) {
            throw new RuntimeException("Already processed");
        }

        request.setStatus(JoinRequestStatus.APPROVED.name());
        joinRequestRepository.save(request);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectUser pu = ProjectUser.builder()
                .project(project)
                .user(user)
                .userType(UserType.fromValue(user.getUserType()))
                .build();

        projectUserRepository.save(pu);
    }

    @Override
    public boolean isProjectOwner(UUID projectId, UUID userId) {
        return projectRepository.findById(projectId)
                .map(project -> project.getOwner().getId().equals(userId))
                .orElse(false);
    }

    @Override
    public boolean isOwnerOrMember(UUID projectId, UUID userId) {
        return isProjectOwner(projectId, userId) ||
                projectUserRepository.existsByProjectIdAndUserId(projectId, userId);
    }
}
