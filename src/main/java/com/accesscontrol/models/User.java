package com.accesscontrol.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.accesscontrol.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(exclude = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

@Id
@GeneratedValue
private UUID id;

@Column(unique = true)
private String username;
@Column(unique = true)
private String email;
private int userType;

@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<Comment> comments = new ArrayList<>();


public UserType getUserTypeEnum() {
    return UserType.fromValue(this.userType);
}

public void setUserTypeEnum(UserType type) {
    this.userType = type.getValue();
}

private String password;

private String fullname; 

}
