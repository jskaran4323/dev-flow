package com.accesscontrol.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import com.accesscontrol.models.Issue;
import com.accesscontrol.models.Project;

public interface IssueRepository extends JpaRepository<Issue, UUID> {
    Page<Issue> findByProject(Project Project, Pageable pageable);
    
    Page<Issue> findByAssigneeId(UUID assigneeId, Pageable pageable);

    
}
