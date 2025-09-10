package com.accesscontrol.models;

import com.accesscontrol.enums.ProjectStatusType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "project")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Project {
  @Id @GeneratedValue() private UUID id;
  private String name;
  private String description;

  @Column(name = "status")
  private int status;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  public ProjectStatusType getProjectStatusEnum() {
    return ProjectStatusType.fromValue(this.status);
  }

  public void setProjectStatusEnum(ProjectStatusType value) {
    this.status = value.getValue();
  }

  @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Issue> issues;

  @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Label> labels;

  @CreatedDate private LocalDateTime createdAt;

  @LastModifiedDate private LocalDateTime updatedAt;
}
