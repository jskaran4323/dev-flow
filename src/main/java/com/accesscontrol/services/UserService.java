package com.accesscontrol.services;

import com.accesscontrol.models.User;
import com.accesscontrol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String username, String password, String fullName, Set<String> roles){
        if (userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("Username already exists");
        }
              User user = User.builder()
              .username(username).
              password(passwordEncoder.encode(password)).fullname(fullName).roles(roles).build();
              return userRepository.save(user);
   

    }
    public List<User> getUser(){
       return userRepository.findAll();
    }

    public User userById(Long id){
        return userRepository.findById(id).orElse(new User());
     }
}
