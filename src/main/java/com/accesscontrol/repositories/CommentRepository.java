package com.accesscontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.accesscontrol.models.Comment;

import com.accesscontrol.models.User;
import com.accesscontrol.models.Issue;
import java.util.*;


public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByIssue(Issue issue, Pageable pageable);
    
    List<Comment> findByAuthor(User user);
}
