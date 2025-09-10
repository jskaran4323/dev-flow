package com.accesscontrol.impl;

import com.accesscontrol.dto.request.CommentRequest;
import com.accesscontrol.models.Comment;
import com.accesscontrol.models.Issue;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.CommentRepository;
import com.accesscontrol.repositories.IssueRepository;
import com.accesscontrol.services.CommentService;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired private CommentRepository commentRepository;
  @Autowired private IssueRepository issueRepository;

  @Override
  public Comment createComment(UUID issueId, CommentRequest request, User author) {
    Issue issue =
        issueRepository
            .findById(issueId)
            .orElseThrow(() -> new IllegalArgumentException("Issue not found"));

    Comment comment =
        Comment.builder()
            .content(request.getContent())
            .issue(issue)
            .author(author)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    return commentRepository.save(comment);
  }

  @Override
  public List<Comment> getCommentsByIssueId(UUID issueId) {
    return issueRepository
        .findWithCommentsById(issueId)
        .map(Issue::getComments)
        .orElse(Collections.emptyList());
  }

  @Override
  public Optional<Comment> getCommentById(UUID issueId, UUID commentId) {
    return commentRepository.findById(commentId).filter(c -> c.getIssue().getId().equals(issueId));
  }

  @Override
  public Optional<Comment> updateComment(UUID issueId, UUID commentId, CommentRequest request) {
    return getCommentById(issueId, commentId)
        .map(
            existing -> {
              existing.setContent(request.getContent());
              existing.setUpdatedAt(LocalDateTime.now());
              return commentRepository.save(existing);
            });
  }

  @Override
  public void deleteComment(UUID issueId, UUID commentId) {
    getCommentById(issueId, commentId).ifPresent(commentRepository::delete);
  }

  @Override
  public List<Comment> getAllComments() {
    return commentRepository.findAll();
  }
}
