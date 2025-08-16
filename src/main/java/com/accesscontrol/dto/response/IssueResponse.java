package com.accesscontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.accesscontrol.dto.CommentDto;
import com.accesscontrol.dto.LabelDto;
import com.accesscontrol.enums.IssueType;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class IssueResponse {
    private UUID id;
    private String title;
    private String description;
    private IssueType status;
    private List<LabelDto> labels;
    private List<CommentDto> comments;
    private ProjectUserResponse assignee; 
    private ProjectResponse project; 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}