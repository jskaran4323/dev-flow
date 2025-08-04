package com.accesscontrol.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ProjectResponse {
    private UUID id;
    private String name;
    private String description;
    private String ownerUsername;
}