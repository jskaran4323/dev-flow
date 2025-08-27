package com.accesscontrol.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@Entity
@Table(name="comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Comment {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;
   
   @Column(columnDefinition = "TEXT")
   private String content;
   
   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "author_id")
  
   private User author;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "issue_id")
 
   private Issue issue;
    
     @CreatedDate
    private LocalDateTime createdAt;


    @LastModifiedDate
    private LocalDateTime updatedAt;
}
