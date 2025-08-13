package com.accesscontrol.models;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.accesscontrol.enums.UserType;

import jakarta.persistence.*;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "project_users")
public class ProjectUser {
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @ManyToOne
    
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "User_type", nullable = false)
    private UserType userType;

  
}
