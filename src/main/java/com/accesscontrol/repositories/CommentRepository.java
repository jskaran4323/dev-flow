package com.accesscontrol.repositories;

import com.accesscontrol.models.Comment;
import com.accesscontrol.models.Issue;
import com.accesscontrol.models.Project;
import com.accesscontrol.models.User;
import java.util.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
  List<Comment> findByIssue(Issue issue);

  List<Comment> findByAuthor(User user);

  List<Comment> findByProject(Optional<Project> project);
}
