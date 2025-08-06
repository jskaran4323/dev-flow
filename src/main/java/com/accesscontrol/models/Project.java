package com.accesscontrol.models;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.accesscontrol.enums.ProjectStatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "project")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Project {
    @Id
    @GeneratedValue()
    private UUID id;
    private String name;
    private String description;
    @Column(name = "status")
    private ProjectStatusType status;
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    


@OneToMany(mappedBy = "project")
private List<Issue> issues;

@OneToMany(mappedBy = "project")

private List<Label> labels;


    @CreatedDate
    private LocalDateTime createdAt;


    @LastModifiedDate
    private LocalDateTime updatedAt;
}
