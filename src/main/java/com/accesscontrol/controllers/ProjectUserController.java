package com.accesscontrol.controllers;

import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.dto.request.AddTeamMemberRequest;
import com.accesscontrol.dto.response.ProjectUserResponse;
import com.accesscontrol.services.ProjectUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectUserController {

    private final ProjectUserService projectUserService;

//  GET /api/projects/{projectId}/team
    @GetMapping("/{projectId}/team")
    public ResponseEntity<List<ProjectUserResponse>> getTeam(@PathVariable UUID projectId) {
        List<ProjectUserResponse> team = projectUserService.getTeam(projectId);
        return ResponseEntity.ok(team);
    }

    // POST /api/projects/{projectId}/team
    @PostMapping("/{projectId}/team")
    public ResponseEntity<Void> addTeamMember(
            @PathVariable UUID projectId,
            @RequestBody AddTeamMemberRequest request
    ) {
        projectUserService.addTeamMember(projectId, request);
        return ResponseEntity.ok().build();
    }
}

