package com.accesscontrol.dto;

import java.util.UUID;

import lombok.Data;


@Data
public class IssueRequest {
    private String title;
    private String description;
    private int status;
    private UUID assigneeId; 
}
