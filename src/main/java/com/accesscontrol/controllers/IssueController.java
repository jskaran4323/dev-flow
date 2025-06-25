package com.accesscontrol.controllers;

import java.util.*;

import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.dto.request.IssueRequest;
import com.accesscontrol.dto.response.IssueResponse;
import com.accesscontrol.mapper.IssueMapper;
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
@RequestMapping("/api/{projectId}/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;
    @PostMapping
    public ResponseEntity<IssueResponse> createEntity(
        @PathVariable UUID projectId,
        @RequestBody IssueRequest request,
        Authentication auth
    ) {
       
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
    
       
        UUID assigneeId = request.getAssigneeId();
        User assignee;
    
        if (assigneeId != null) {
            Optional<User> assigneeOpt = userRepository.findById(assigneeId);
            if (assigneeOpt.isEmpty()) {
                return ResponseEntity.badRequest().build(); 
            }
            assignee = assigneeOpt.get();
        } else {
            
            assignee = (User) auth.getPrincipal();
        }
    
        
        Set<Label> labels = new HashSet<>();
        if (request.getLabelIds() != null && !request.getLabelIds().isEmpty()) {
            labels.addAll(labelRepository.findAllById(request.getLabelIds()));
        }
    
       
        Issue issue = Issue.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .assignee(assignee)
                .project(projectOpt.get())
                .labels(labels)
                .build();
    
        Issue saved = issueService.createIssue(issue);
        return ResponseEntity.ok(IssueMapper.toIssueResponse(saved));
    }
    @GetMapping("/{issueId}")
    public ResponseEntity<IssueResponse> getSingleIssue(@PathVariable UUID issueId){
        return issueService.getIssueById(issueId).map(IssueMapper::toIssueResponse).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
     
    @PutMapping("/{issueId}")
    public ResponseEntity<IssueResponse> updateIssue(@PathVariable UUID projectId, @PathVariable UUID issueId,
    @RequestBody IssueRequest request){
        Optional<User> assigneeOpt = userRepository.findById(request.getAssigneeId());
        Set<Label> labels = new HashSet<>(labelRepository.findAllById(request.getLabelIds()));

        return issueService.getIssueById(issueId).map(existing-> {
            existing.setTitle(request.getTitle());
            existing.setDescription(request.getDescription());
            existing.setStatus(request.getStatus());
            assigneeOpt.ifPresent(existing::setAssignee);
            Issue updated = issueService.updateIssue(existing);
            existing.setLabels(labels);
            return ResponseEntity.ok(IssueMapper.toIssueResponse(updated));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable UUID issueId) {
        issueService.deleteIssue(issueId);
        return ResponseEntity.noContent().build();
    }
    



}
