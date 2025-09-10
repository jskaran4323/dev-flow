package com.accesscontrol.controllers;

import com.accesscontrol.dto.CommentDto;
import com.accesscontrol.dto.request.CommentRequest;
import com.accesscontrol.mapper.CommentMapper;
import com.accesscontrol.models.Comment;
import com.accesscontrol.models.CustomUserDetails;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.UserRepository;
import com.accesscontrol.services.CommentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  private final UserRepository userRepository;

  /**
   * @param issueId
   * @param request
   * @param auth
   * @return add comments for a given issue POST api/issues/issue_id/comments
   */
  @PostMapping("/{issueId}/comments")
  public ResponseEntity<CommentDto> createComment(
      @PathVariable UUID issueId, @RequestBody CommentRequest request, Authentication auth) {
    User author;
    if (request.getAuthorId() != null) {
      author =
          userRepository
              .findById(request.getAuthorId())
              .orElseThrow(() -> new IllegalArgumentException("Author not found"));
    } else {
      CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
      author = userDetails.getUser();
    }

    Comment created = commentService.createComment(issueId, request, author);
    return ResponseEntity.ok(CommentMapper.toDto(created));
  }

  /**
   * @param issueId
   * @return list of comments GET api/issues/issue_id/comments
   */
  @GetMapping("/{issueId}/comments")
  public ResponseEntity<List<CommentDto>> getIssueComments(@PathVariable UUID issueId) {
    List<Comment> comments = commentService.getCommentsByIssueId(issueId);
    return ResponseEntity.ok(comments.stream().map(CommentMapper::toDto).toList());
  }

  /**
   * @param issueId
   * @param commentId
   * @return
   */
  @GetMapping("/{issueId}/comments/{commentId}")
  public ResponseEntity<CommentDto> getSingleComment(
      @PathVariable UUID issueId, @PathVariable UUID commentId) {
    return commentService
        .getCommentById(issueId, commentId)
        .map(CommentMapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{issueId}/comments/{commentId}")
  public ResponseEntity<CommentDto> updateComment(
      @PathVariable UUID issueId,
      @PathVariable UUID commentId,
      @RequestBody CommentRequest request) {
    return commentService
        .updateComment(issueId, commentId, request)
        .map(CommentMapper::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{issueId}/comments/{commentId}")
  public ResponseEntity<Void> deleteComment(
      @PathVariable UUID issueId, @PathVariable UUID commentId) {
    commentService.deleteComment(issueId, commentId);
    return ResponseEntity.noContent().build();
  }
}
