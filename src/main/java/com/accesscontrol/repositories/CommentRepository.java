package com.accesscontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;


import com.accesscontrol.models.Comment;

import com.accesscontrol.models.User;
import com.accesscontrol.models.Issue;
import com.accesscontrol.models.Project;

import java.util.*;
import java.util.List;



public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByIssue(Issue issue);
    List<Comment> findByAuthor(User user);
    List<Comment> findByProject(Optional<Project> project);
    
}
