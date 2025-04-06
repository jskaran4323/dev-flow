package com.accesscontrol.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(unique = true)
private String username;
private String password;
private String fullname;

@ElementCollection(fetch = FetchType.EAGER)
@CollectionTable(name= "user_roles", joinColumns = @JoinColumn(name = "user_id"))
@Column(name = "role")

private Set<String> roles;


  
    
}
