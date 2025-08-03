package com.accesscontrol.controllers;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.request.ApproveRequestDto;
import com.accesscontrol.dto.request.JoinRequestDto;
import com.accesscontrol.dto.response.ProjectUserResponse;
import com.accesscontrol.models.User;
import com.accesscontrol.services.ProjectUserService;


import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectUserController {
  
    private final ProjectUserService projectUserService;


    @GetMapping("/{projectId}/team")
    public ResponseEntity<List<ProjectUserResponse>> getTeam(@PathVariable UUID projectId) {
        List<ProjectUserResponse> team = projectUserService.getTeam(projectId);
        return ResponseEntity.ok(team);
    }

    
    @PostMapping("/{projectId}/team")
    public ResponseEntity<Void> addTeamMember(
            @PathVariable UUID projectId,
            @RequestBody AddTeamMemberRequest request
    ) {
        projectUserService.addTeamMember(projectId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/join-request")
    public ResponseEntity<Void> requestToJoin(
        @PathVariable UUID projectId,
        Authentication auth
    ) {
        
        User principal = (User) auth.getPrincipal(); 
        UUID userId = principal.getId();
        projectUserService.requestToJoin(projectId, userId);
        return ResponseEntity.ok().build();
    }

    
    @GetMapping("/{projectId}/join-requests")
    public ResponseEntity<List<JoinRequestDto>> getJoinRequests(
        @PathVariable UUID projectId,
        Authentication auth
    ) {
        User principal = (User) auth.getPrincipal(); 
        UUID userId = principal.getId();
        List<JoinRequestDto> requests = projectUserService.getJoinRequests(projectId, userId);
        return ResponseEntity.ok(requests);
    }

    
    @PostMapping("/{projectId}/approve-request")
    public ResponseEntity<Void> approveRequest(
        @PathVariable UUID projectId,
        @RequestBody ApproveRequestDto request,
        Authentication auth
    ) {
        User principal = (User) auth.getPrincipal(); 
        UUID userId = principal.getId();
        projectUserService.approveJoinRequest(projectId, request.getUserId(), userId);
        return ResponseEntity.ok().build();
    }


}

