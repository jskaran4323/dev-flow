package com.accesscontrol.repositories;

import com.accesscontrol.models.Project;
import com.accesscontrol.models.ProjectUser;
import com.accesscontrol.models.User;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
public interface ProjectRepository extends JpaRepository<Project, UUID>{
    List<Project> findByOwner(User user);
    List<Project> findByOwnerId(UUID id);
    List<Project> findAll();
    
    } 
