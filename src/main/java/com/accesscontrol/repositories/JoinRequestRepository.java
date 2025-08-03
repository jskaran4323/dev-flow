package com.accesscontrol.repositories;

import com.accesscontrol.models.ProjectJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JoinRequestRepository extends JpaRepository<ProjectJoinRequest, UUID> {
    Optional<ProjectJoinRequest> findByProjectIdAndUserId(UUID projectId, UUID userId);
    List<ProjectJoinRequest> findByProjectIdAndStatus(UUID projectId, String status);
}
