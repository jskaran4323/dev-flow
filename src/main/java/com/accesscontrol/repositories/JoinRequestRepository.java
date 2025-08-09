package com.accesscontrol.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesscontrol.models.ProjectJoinRequest;

@Repository
public interface JoinRequestRepository extends JpaRepository<ProjectJoinRequest, UUID> {
    Optional<ProjectJoinRequest> findByProjectIdAndUserId(UUID projectId, UUID userId);
    boolean existsByProjectIdAndUserIdAndStatus(UUID projectId, UUID userId, String status);
    List<ProjectJoinRequest> findByProjectIdAndStatus(UUID projectId, String status);
}
