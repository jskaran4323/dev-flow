package com.accesscontrol.mapper;

import com.accesscontrol.dto.CommentDto;
import com.accesscontrol.dto.LabelDto;
import com.accesscontrol.dto.response.*;
import com.accesscontrol.models.*;
import com.accesscontrol.enums.UserType;

import java.util.*;

public class IssueMapper {
        
    public static IssueResponse toIssueResponse(Issue issue) {
        List<LabelDto> labelDtos = Optional.ofNullable(issue.getLabels())
           .orElse(Collections.emptySet()).stream()
            .map(label -> new LabelDto(label.getId(), label.getType()))
            .toList();

        List<CommentDto> commentDtos = Optional.ofNullable(issue.getComments())
        .orElse(Collections.emptyList())
        .stream()
        .map(c -> new CommentDto(c.getId(), c.getContent(), c.getCreatedAt(), c.getUpdatedAt()))
        .toList();
                
        // Map assignee to ProjectUserResponse (avoid circular reference)
        ProjectUserResponse assigneeResponse = null;
        if (issue.getAssignee() != null) {
            User assignee = issue.getAssignee();
            // Convert int userType to enum using your fromValue method
            UserType userTypeEnum = UserType.fromValue(assignee.getUserType());
            assigneeResponse = new ProjectUserResponse(
                assignee.getId(),
                assignee.getFullname(),
                assignee.getUsername(),
                userTypeEnum
            );
        }
                          
        return new IssueResponse(
            issue.getId(),
            issue.getTitle(),
            issue.getDescription(),
            issue.getStatus(),
            labelDtos,
            commentDtos,
            assigneeResponse,  // Add assignee to response
            issue.getCreatedAt(),
            issue.getUpdatedAt()
        );
    }
}