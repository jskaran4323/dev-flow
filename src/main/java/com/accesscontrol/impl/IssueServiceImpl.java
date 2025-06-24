package com.accesscontrol.impl;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.accesscontrol.models.Issue;
import com.accesscontrol.repositories.IssueRepository;
import com.accesscontrol.services.IssueService;

@Service
public class IssueServiceImpl implements IssueService{
    @Autowired
    private IssueRepository issueRepository;
    @Override
    public Issue createIssue(Issue issue) {
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        return issueRepository.save(issue);
        }

    @Override
    public Optional<Issue> getIssueById(UUID id) {
     return issueRepository.findById(id);    
    }
     //TODO: implement pagination for this
    // @Override
    // public Page<Issue> getIssueByProject(Project project, Pageable pageable) {
    //     return issueRepository.findByProject(project);
    // }

    @Override
    public Issue updateIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(UUID id) {
        issueRepository.deleteById(id);
    }

    @Override
    public Page<Issue> findByAssigneeId(UUID assigneeId, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByAssigneeId'");
    }
    
}
