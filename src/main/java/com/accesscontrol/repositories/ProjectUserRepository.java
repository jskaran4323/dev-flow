package com.accesscontrol.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesscontrol.models.Project;
import com.accesscontrol.models.ProjectUser;
import com.accesscontrol.models.User;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    List<ProjectUser> findByProjectId(UUID projectId);
    boolean existsByProjectAndUser(Project project, User user);

    Optional<ProjectUser> findByProjectIdAndUserId(UUID projectId, UUID userId);
}

