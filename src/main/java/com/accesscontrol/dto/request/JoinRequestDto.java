package com.accesscontrol.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class JoinRequestDto {
    private UUID userId;
    private String username;
    private String fullName;
    private String status; // "PENDING", "APPROVED", "REJECTED"
    private LocalDateTime createdAt;
}
