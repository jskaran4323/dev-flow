package com.accesscontrol.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.accesscontrol.models.Issue;
import com.accesscontrol.models.User;
import com.accesscontrol.config.CustomUserDetails;
import com.accesscontrol.dto.CommentDto;
import com.accesscontrol.dto.request.CommentRequest;
import com.accesscontrol.mapper.CommentMapper;
import com.accesscontrol.models.Comment;

import com.accesscontrol.repositories.IssueRepository;
import com.accesscontrol.repositories.UserRepository;
import com.accesscontrol.services.CommentService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/issues/{issueId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService; 
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
        @PathVariable UUID issueId,
        @RequestBody CommentRequest request,
            Authentication auth
    ){
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        UUID authorId = request.getAuthorId();
        User author;
    
        if (authorId != null) {
            Optional<User> authorOpt = userRepository.findById(authorId);
            if (authorOpt.isEmpty()) {
                return ResponseEntity.badRequest().build(); 
            }
            author = authorOpt.get();
        } else {
             CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            author = userDetails.getUser();
        }
      

        Comment comment = Comment.builder()
            .content(request.getContent())
            .author(author)
            .issue(issueOpt.get())
            .build();
        
          Comment saved = commentService.createComment(comment);
           return ResponseEntity.ok(CommentMapper.toDto(saved));
    }

    //TODO: showing all comments need to filter through project id
  @GetMapping
public ResponseEntity<List<CommentDto>> getAllComments() {
    List<Comment> comments = commentService.getAllComments();
    List<CommentDto> dtos = comments.stream()
        .map(CommentMapper::toDto)
        .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
}

    
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getSingleComment(@PathVariable UUID commentId){
        return commentService.getByCommentId(commentId)
            .map(CommentMapper::toDto).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
        @PathVariable UUID commentId,
        @RequestBody Comment updatedComment
    ) {
        return commentService.getByCommentId(commentId).map(existing -> {
            existing.setContent(updatedComment.getContent());
            Comment updated  = commentService.updateComment(existing);
            return ResponseEntity.ok(CommentMapper.toDto(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
