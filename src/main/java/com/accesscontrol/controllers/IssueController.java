package com.accesscontrol.controllers;

import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.dto.request.IssueRequest;
import com.accesscontrol.dto.response.IssueResponse;
import com.accesscontrol.mapper.IssueMapper;
import com.accesscontrol.models.CustomUserDetails;
import com.accesscontrol.models.Issue;
import com.accesscontrol.models.Label;
import com.accesscontrol.models.Project;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.LabelRepository;
import com.accesscontrol.repositories.ProjectRepository;
import com.accesscontrol.services.IssueService;
import com.accesscontrol.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;
    
    @PostMapping("/{projectId}")
    public ResponseEntity<IssueResponse> createEntity(
        @PathVariable UUID projectId,
        @RequestBody IssueRequest request,
        Authentication auth
    ) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
    
        Project project = projectOpt.get();
    
        UUID assigneeId = request.getAssigneeId();
        User assignee;
    
        if (assigneeId != null) {
            Optional<User> assigneeOpt = userRepository.findById(assigneeId);
            if (assigneeOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            assignee = assigneeOpt.get();
        } else {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            assignee = userDetails.getUser(); 
        }
    
    
        
       
        Set<Label> labels = new HashSet<>();
        if (request.getLabels() != null && !request.getLabels().isEmpty()) {
            for (Integer labelType : request.getLabels()) {
                Label existingLabel = labelRepository.findByProjectAndType(project, labelType);
                
                if (existingLabel != null) {
                    labels.add(existingLabel);
                } else {
                    Label newLabel = new Label();
                    newLabel.setType(labelType);
                    newLabel.setProject(project);
                    Label savedLabel = labelRepository.save(newLabel);
                    labels.add(savedLabel);
                }
            }
        }
        
        Issue issue = Issue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .assignee(assignee)
                .project(project)
                .labels(labels)
                .build();
        
        Issue saved = issueService.createIssue(issue);
        return ResponseEntity.ok(IssueMapper.toIssueResponse(saved));
    }
    
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<IssueResponse>> getProjectIssue(
        @PathVariable UUID projectId, 
        Authentication auth
    ) {
        List<Issue> issues = issueService.getProjectIssues(projectId);
        List<IssueResponse> response = issues.stream()
            .map(IssueMapper::toIssueResponse)
            .toList();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{issueId}")
    public ResponseEntity<IssueResponse> getSingleIssue(
        @PathVariable UUID issueId,
        Authentication auth
    ) {
        return issueService.getIssueById(issueId)
            .map(IssueMapper::toIssueResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
     
    @PutMapping("/{issueId}")
    public ResponseEntity<IssueResponse> updateIssue(
        @PathVariable UUID issueId,
        @RequestBody IssueRequest request,
        Authentication auth
    ) {
        return issueService.getIssueById(issueId).map(existing -> {
            // Update basic fields
            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setStatus(request.getStatus());
            
            // Update assignee if provided
            if (request.getAssigneeId() != null) {
                Optional<User> assigneeOpt = userRepository.findById(request.getAssigneeId());
                assigneeOpt.ifPresent(existing::setAssignee);
            }
            
         
            Set<Label> labels = new HashSet<>();
            if (request.getLabels() != null && !request.getLabels().isEmpty()) {
                Project project = existing.getProject();
                for (Integer labelType : request.getLabels()) {
                  
                    Label existingLabel = labelRepository.findByProjectAndType(project, labelType);
                    
                    if (existingLabel != null) {
                        labels.add(existingLabel);
                    } else {
                
                        Label newLabel = new Label();
                        newLabel.setType(labelType);
                        newLabel.setProject(project);
                        Label savedLabel = labelRepository.save(newLabel);
                        labels.add(savedLabel);
                    }
                }
            }
            existing.setLabels(labels);
            
            Issue updated = issueService.updateIssue(existing);
            return ResponseEntity.ok(IssueMapper.toIssueResponse(updated));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable UUID issueId) {
        issueService.deleteIssue(issueId);
        return ResponseEntity.noContent().build();
    }
}