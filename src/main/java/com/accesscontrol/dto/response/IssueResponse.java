package com.accesscontrol.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.accesscontrol.models.Comment;
import com.accesscontrol.models.Label;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class IssueResponse {
    
    private UUID id;
    private String title;
    private String description;
    private int status;
    private UUID assigneeId;
    private String assigneeName;
    private List<Label> labels;
    private List<Comment> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


