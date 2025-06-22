package com.accesscontrol.models;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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

    @ManyToOne
    private User owner;
    

    @CreatedDate
    private LocalDateTime createdAt;


    @LastModifiedDate
    private LocalDateTime updatedAt;
}
