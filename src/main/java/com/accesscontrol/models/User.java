package com.accesscontrol.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

import com.accesscontrol.enums.UserType;
@Entity
@Table(name = "users")
@Data
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


public UserType getUserTypeEnum() {
    return UserType.fromValue(this.userType);
}

public void setUserTypeEnum(UserType type) {
    this.userType = type.getValue();
}

private String password;

private String fullname;

}
