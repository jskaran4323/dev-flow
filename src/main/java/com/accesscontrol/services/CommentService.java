package com.accesscontrol.services;

import com.accesscontrol.dto.request.CommentRequest;
import com.accesscontrol.models.Comment;
import com.accesscontrol.models.User;
import java.util.*;

public interface CommentService {

  Comment createComment(UUID issueId, CommentRequest request, User author);

  List<Comment> getCommentsByIssueId(UUID issueId);

  Optional<Comment> getCommentById(UUID issueId, UUID commentId);

  Optional<Comment> updateComment(UUID issueId, UUID commentId, CommentRequest request);

  List<Comment> getAllComments();

  void deleteComment(UUID issueId, UUID commentId);
}
