package com.accesscontrol.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_join_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectJoinRequest {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID projectId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String status; // e.g., "PENDING", "APPROVED", "REJECTED"

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
