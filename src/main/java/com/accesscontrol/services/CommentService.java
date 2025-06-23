package com.accesscontrol.services;

import com.accesscontrol.models.Comment;
import java.util.*;
public interface CommentService {
    Comment createComment(Comment comment);
    Optional<Comment> getByCommentId(UUID id);
    List<Comment> getCommentByIssueId(UUID id);
    List<Comment> getCommentByUserId(UUID id);
    Comment updateComment(Comment comment);
    void deleteComment(UUID id);

}
