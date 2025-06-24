package com.accesscontrol.controllers;

import java.util.*;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.dto.request.IssueRequest;
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
    public ResponseEntity<Issue> createEntity(@PathVariable UUID projectId, @RequestBody IssueRequest request){
       Optional<Project> projectOpt = projectRepository.findById(projectId);
       Optional<User> assigneeOpt = userRepository.findById(request.getAssigneeId());
       Set<Label> labels = new HashSet<>();
        labels = new HashSet<>(labelRepository.findAllById(request.getLabelIds()));
       if(projectOpt.isEmpty()){
        return ResponseEntity.notFound().build();
       }
       Issue issue = Issue.builder()
       .title(request.getTitle())
       .description(request.getDescription())
       .status(request.getStatus())
       .assignee(assigneeOpt.get())
       .project(projectOpt.get())
       .labels(labels)
       .build();
       return ResponseEntity.ok(issueService.createIssue(issue));
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getSingleIssue(@PathVariable UUID issueId){
        return issueService.getIssueById(issueId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
     
    @PutMapping("/{issueId}")
    public ResponseEntity<Issue> updateIssue(@PathVariable UUID projectId, @PathVariable UUID issueId,
    @RequestBody Issue updatedIssue){
        
        return issueService.getIssueById(issueId).map(existing-> {
            existing.setTitle(updatedIssue.getTitle());
            existing.setDescription(updatedIssue.getDescription());
            existing.setStatus(updatedIssue.getStatus());
            existing.setAssignee(updatedIssue.getAssignee());
            return ResponseEntity.ok(issueService.updateIssue(existing));
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable UUID issueId) {
        issueService.deleteIssue(issueId);
        return ResponseEntity.noContent().build();
    }
    



}
