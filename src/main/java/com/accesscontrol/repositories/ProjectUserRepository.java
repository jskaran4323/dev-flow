package com.accesscontrol.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesscontrol.models.Project;
import com.accesscontrol.models.ProjectUser;
import com.accesscontrol.models.User;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    // ✅ Get all team members of a project
    List<ProjectUser> findByProjectId(UUID projectId);

    // ✅ Check if a specific user is already in the project
    boolean existsByProjectIdAndUserId(UUID projectId, UUID userId);
    boolean existsByProjectAndUser(Project project, User user);

    // ✅ Find project-user link (used to prevent duplicates)
    Optional<ProjectUser> findByProjectIdAndUserId(UUID projectId, UUID userId);

    // ✅ Get all projects a user is part of
    List<ProjectUser> findByUserId(UUID userId);
}
