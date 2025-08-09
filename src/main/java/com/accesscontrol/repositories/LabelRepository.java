package com.accesscontrol.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesscontrol.models.Issue;
import com.accesscontrol.models.Label;
import com.accesscontrol.models.Project;
public interface LabelRepository extends JpaRepository<Label, UUID>{
    List<Label> findByProject(Project project);
    //List<Label> findByIssue(Issue issue);
    Label findByProjectAndType(Project project, Integer type);
}
