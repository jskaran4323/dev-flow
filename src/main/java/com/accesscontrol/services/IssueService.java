package com.accesscontrol.services;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.accesscontrol.models.Issue;
import com.accesscontrol.models.Project;

public interface IssueService {
    Issue createIssue(Issue issue);
    Optional<Issue> getIssueById(UUID id);
    Page<Issue> getIssueByProject(Project project);
    //TODO: make more get models
       Page<Issue> findByAssigneeId(UUID assigneeId, Pageable pageable);
    public Issue updateIssue(Issue issue);
    public void deleteIssue(UUID id);
    
}
