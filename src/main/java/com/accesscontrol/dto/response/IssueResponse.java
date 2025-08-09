package com.accesscontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.accesscontrol.dto.CommentDto;
import com.accesscontrol.dto.LabelDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {
    private UUID id;
    private String title;
    private String description;
    private int status;
    private List<LabelDto> labels;
    private List<CommentDto> comments;
    private ProjectUserResponse assignee;  // ADD THIS FIELD
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}