package com.accesscontrol.models;

import com.accesscontrol.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id @GeneratedValue private UUID id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private int userType;

  private String password;

  private String fullname;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Comment> comments = new ArrayList<>();

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public UserType getUserTypeEnum() {
    return UserType.fromValue(this.userType);
  }

  public void setUserTypeEnum(UserType type) {
    this.userType = type.getValue();
  }

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
