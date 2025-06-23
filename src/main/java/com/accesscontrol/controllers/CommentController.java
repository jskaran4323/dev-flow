package com.accesscontrol.controllers;

import java.util.Optional;
import java.util.UUID;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.accesscontrol.dto.CommentRequest;
import com.accesscontrol.models.Issue;
import com.accesscontrol.models.User;
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
    public ResponseEntity<Comment> createComment(
        @PathVariable UUID issueId,
        @RequestBody CommentRequest request
    ){
        Optional<Issue> issueOpt = issueRepository.findById(issueId);
        Optional<User> authorOpt = userRepository.findById(request.getAuthorId());
           if (issueOpt.isEmpty() || authorOpt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    Comment comment = Comment.builder().content(request.getContent())
    .author(authorOpt.get()).issue(issueOpt.get()).build();
        
    return ResponseEntity.ok(commentService.createComment(comment));
    }
}
