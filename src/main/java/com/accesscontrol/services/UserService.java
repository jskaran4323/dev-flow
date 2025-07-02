package com.accesscontrol.services;

import com.accesscontrol.dto.response.RegisterRequest;
import com.accesscontrol.enums.UserType;
import com.accesscontrol.models.User;
import com.accesscontrol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    


    public User registerUser(RegisterRequest request){
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
              UserType userType = request.getType() != null ? request.getType() : UserType.VIEWER;
              User user = User.builder()
              .username(request.getUsername()).
              password(passwordEncoder.encode(request.getPassword())).fullname(request.getFullName()).email(request.getEmail()).userType(userType.getValue()).build();
           
              return userRepository.save(user);

   

    }
    public List<User> getUser(){
       return userRepository.findAll();
    }

    public User userById(UUID id){
        return userRepository.findById(id).orElse(new User());
     }
    
     public List<User> getAllCustomers(int type){
        return userRepository.findByUserType(type);
     }

       public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

     public Optional<User> findByUsernameOrEmail(String identifier) {
        return userRepository.findByUsername(identifier)
               .or(() -> userRepository.findByEmail(identifier));
    }
}