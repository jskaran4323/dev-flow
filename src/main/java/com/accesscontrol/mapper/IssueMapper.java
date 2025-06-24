package com.accesscontrol.mapper;

import com.accesscontrol.dto.CommentDto;
import com.accesscontrol.dto.LabelDto;
import com.accesscontrol.dto.response.*;
import com.accesscontrol.models.*;


import java.util.*;

public class IssueMapper {

    public static IssueResponse toIssueResponse(Issue issue) {
        List<LabelDto> labelDtos = Optional.ofNullable(issue.getLabels())
           .orElse(Collections.emptySet()).stream()
            .map(label -> new LabelDto(label.getId(), label.getName(), label.getType()))
            .toList();

        // You can convert to CommentDto later if needed
        List<CommentDto> commentDtos = Optional.ofNullable(issue.getComments())
        .orElse(Collections.emptyList())
        .stream()
        .map(c -> new CommentDto(c.getId(), c.getContent(), c.getCreatedAt(), c.getUpdatedAt()))
        .toList();
        return new IssueResponse(
            issue.getId(),
            issue.getTitle(),
            issue.getDescription(),
            issue.getStatus(),
            issue.getAssignee() != null ? issue.getAssignee().getId() : null,
            issue.getAssignee() != null ? issue.getAssignee().getFullname() : null,
            labelDtos,
            commentDtos,
            issue.getCreatedAt(),
            issue.getUpdatedAt()
        );
    }
}
