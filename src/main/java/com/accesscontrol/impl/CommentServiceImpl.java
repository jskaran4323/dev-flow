package com.accesscontrol.impl;

import java.time.LocalDateTime;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;

import com.accesscontrol.models.Comment;

import com.accesscontrol.models.Issue;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.CommentRepository;
import com.accesscontrol.repositories.IssueRepository;
import com.accesscontrol.repositories.UserRepository;
import com.accesscontrol.services.CommentService;

public class CommentServiceImpl implements CommentService{
 
    @Autowired
    private CommentRepository commentRepository;
    private IssueRepository issueRepository;
    private UserRepository userRepository;
     
    @Override
    public Comment createComment(Comment comment) {
      comment.setCreatedAt(LocalDateTime.now());
      comment.setUpdatedAt(LocalDateTime.now());
      return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentByIssueId(UUID id) {
        return issueRepository.findById(id)
                .map(Issue::getComments)
                .orElse(Collections.emptyList());
    }
    

    @Override
public List<Comment> getCommentByUserId(UUID id) {
    return userRepository.findById(id)
            .map(User::getComments)
            .orElse(Collections.emptyList());
}

    @Override
    public void deleteComment(UUID id) {
      commentRepository.deleteById(id);
    }
    

    @Override
    public Optional<Comment> getByCommentId(UUID id) {
       return commentRepository.findById(id);
    }

    @Override
    public Comment updateComment(Comment comment) {
      return commentRepository.save(comment);
    }
    
}
