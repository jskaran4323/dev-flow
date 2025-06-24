package com.accesscontrol.mapper;

import com.accesscontrol.dto.CommentDto;
import com.accesscontrol.models.Comment;

public class CommentMapper {
        public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }
}
