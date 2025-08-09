package com.accesscontrol.repositories;

import com.accesscontrol.models.Project;
import com.accesscontrol.models.ProjectUser;
import com.accesscontrol.models.User;
import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
public interface ProjectRepository extends JpaRepository<Project, UUID>{
    List<Project> findByOwner(User user);
    List<Project> findByOwnerId(UUID id);
    List<Project> findAll();
    @Query("""
        select distinct p
        from Project p
        left join ProjectUser pu on pu.project = p
        where p.owner.id = :userId
           or pu.user.id = :userId
      """)
List<Project> findOwnedOrMemberProjects(UUID userId);
    } 
