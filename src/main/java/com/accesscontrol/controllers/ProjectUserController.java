package com.accesscontrol.controllers;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.accesscontrol.dto.request.ApproveRequestDto;
import com.accesscontrol.dto.request.JoinRequestDto;
import com.accesscontrol.dto.response.ProjectUserResponse;
import com.accesscontrol.models.CustomUserDetails;
import com.accesscontrol.repositories.ProjectUserRepository;
import com.accesscontrol.repositories.JoinRequestRepository;
import com.accesscontrol.services.ProjectUserService;

import org.springframework.security.core.Authentication;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectUserController {

    private final ProjectUserService projectUserService;
    private final ProjectUserRepository projectUserRepository;
    private final JoinRequestRepository joinRequestRepository;

    
    @PostMapping("/{projectId}/join-request")
    public ResponseEntity<Map<String, String>> requestToJoin(
            @PathVariable UUID projectId,
            Authentication auth
    ) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            UUID userId = userDetails.getUser().getId();

            if (projectUserRepository.existsByProjectIdAndUserId(projectId, userId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Already a team member"));
            }

            if (joinRequestRepository.existsByProjectIdAndUserIdAndStatus(projectId, userId, "PENDING")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Join request already pending"));
            }

            projectUserService.requestToJoin(projectId, userId);
            return ResponseEntity.ok(Map.of("message", "Join request sent"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

  
    @GetMapping("/{projectId}/join-requests")
    public ResponseEntity<List<JoinRequestDto>> getJoinRequests(
            @PathVariable UUID projectId,
            Authentication auth
    ) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            UUID ownerId = userDetails.getUser().getId();

            if (!projectUserService.isProjectOwner(projectId, ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            List<JoinRequestDto> requests = projectUserService.getJoinRequests(projectId, ownerId);
            return ResponseEntity.ok(requests);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * ✅ Approve a Join Request (Owner Only)
     */
    @PostMapping("/{projectId}/approve-request")
    public ResponseEntity<Map<String, String>> approveJoinRequest(
            @PathVariable UUID projectId,
            @RequestBody ApproveRequestDto request,
            Authentication auth
    ) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            UUID ownerId = userDetails.getUser().getId();

            if (!projectUserService.isProjectOwner(projectId, ownerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            projectUserService.approveJoinRequest(projectId, request.getUserId(), ownerId);
            return ResponseEntity.ok(Map.of("message", "Request approved"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error: " + e.getMessage()));
        }
    }

    /**
     * 👥 Get Team Members (Owner or Member Only)
     */
    @GetMapping("/{projectId}/team")
    public ResponseEntity<List<ProjectUserResponse>> getTeam(
            @PathVariable UUID projectId,
            Authentication auth
    ) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            UUID userId = userDetails.getUser().getId();

            if (!projectUserService.isOwnerOrMember(projectId, userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            List<ProjectUserResponse> team = projectUserService.getTeam(projectId);
            return ResponseEntity.ok(team);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    @GetMapping("/{projectId}/join-request/status")
    public ResponseEntity<Map<String, Boolean>> checkJoinRequestStatus(
            @PathVariable UUID projectId,
            Authentication auth
    ) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            UUID userId = userDetails.getUser().getId();

            boolean hasRequest = joinRequestRepository.existsByProjectIdAndUserIdAndStatus(projectId, userId, "PENDING");
            return ResponseEntity.ok(Map.of("hasPendingRequest", hasRequest));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
